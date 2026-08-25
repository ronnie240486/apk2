package com.xplay.ibotv.mac;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Locale;
import java.util.regex.Pattern;

/** Obtém o identificador de rede disponível sem pedir usuário, senha ou READ_PHONE_STATE. */
public final class MacAddressProvider {
    private static final Pattern HEX_MAC = Pattern.compile("^[0-9A-Fa-f]{12}$");
    private static final String ZERO_MAC = "000000000000";
    private static final String ANDROID_PLACEHOLDER = "020000000000";

    private MacAddressProvider() {
    }

    @SuppressLint("HardwareIds")
    public static String getMacAddress(Context context) {
        String value = null;
        if (context != null) {
            try {
                WifiManager manager = (WifiManager) context.getApplicationContext()
                        .getSystemService(Context.WIFI_SERVICE);
                if (manager != null) {
                    WifiInfo info = manager.getConnectionInfo();
                    if (info != null) {
                        value = normalize(info.getMacAddress());
                    }
                }
            } catch (Exception ignored) {
                // Alguns Androids/TV Boxes bloqueiam WifiInfo.getMacAddress().
            }
        }
        if (value != null) {
            return value;
        }

        try {
            for (NetworkInterface network : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                String name = network.getName();
                if (name == null || network.isLoopback() || !isCandidateInterface(name)) {
                    continue;
                }
                byte[] hardware = network.getHardwareAddress();
                if (hardware == null || hardware.length != 6) {
                    continue;
                }
                StringBuilder mac = new StringBuilder(17);
                for (byte part : hardware) {
                    if (mac.length() > 0) {
                        mac.append(':');
                    }
                    mac.append(String.format(Locale.US, "%02X", part & 0xFF));
                }
                value = normalize(mac.toString());
                if (value != null) {
                    return value;
                }
            }
        } catch (Exception ignored) {
            // Continua tentando as interfaces expostas pelo sistema.
        }

        // Algumas TV Boxes expõem o MAC da interface mesmo quando as APIs Android o ocultam.
        String fromSysfs = readSysfsInterfaces();
        if (fromSysfs != null) {
            return fromSysfs;
        }
        return null;
    }

    private static String readSysfsInterfaces() {
        File root = new File("/sys/class/net");
        File[] interfaces = root.listFiles();
        if (interfaces == null) {
            interfaces = new File[]{new File(root, "wlan0"), new File(root, "eth0"), new File(root, "wifi0")};
        }
        for (File network : interfaces) {
            if (network == null || !isCandidateInterface(network.getName())) {
                continue;
            }
            String value = readFirstLine(new File(network, "address"));
            String normalized = normalize(value);
            if (normalized != null) {
                return normalized;
            }
        }
        return null;
    }

    private static String readFirstLine(File path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.readLine();
        } catch (Exception ignored) {
            return null;
        }
    }

    private static boolean isCandidateInterface(String name) {
        String normalized = name.toLowerCase(Locale.US);
        return normalized.startsWith("wlan") || normalized.startsWith("eth")
                || normalized.startsWith("en") || normalized.startsWith("wifi");
    }

    /** Retorna AA:BB:CC:DD:EE:FF ou null se o valor não for um MAC utilizável. */
    public static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String compact = value.replace(":", "")
                .replace("-", "")
                .replace(".", "")
                .trim();
        if (!HEX_MAC.matcher(compact).matches()
                || ZERO_MAC.equalsIgnoreCase(compact)
                || ANDROID_PLACEHOLDER.equalsIgnoreCase(compact)) {
            return null;
        }
        StringBuilder result = new StringBuilder(17);
        for (int i = 0; i < compact.length(); i += 2) {
            if (result.length() > 0) {
                result.append(':');
            }
            result.append(compact.substring(i, i + 2).toUpperCase(Locale.US));
        }
        return result.toString();
    }
}
