#!/usr/bin/env python3
from pathlib import Path
import re
import sys
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
MAC = ROOT / "decompiled/sources/com/xplay/ibotv/mac"
MANIFESTS = [ROOT / "decompiled/resources/AndroidManifest.xml", ROOT / "analysis/AndroidManifest.xml"]
required = [
    MAC / "MacAddressProvider.java",
    MAC / "MacPanelClient.java",
    MAC / "MacPanelHeartbeat.java",
    MAC / "MacGateActivity.java",
    MAC / "MacHomeActivity.java",
]
for path in required:
    if not path.is_file():
        raise AssertionError(f"arquivo ausente: {path}")

client = (MAC / "MacPanelClient.java").read_text(encoding="utf-8")
provider = (MAC / "MacAddressProvider.java").read_text(encoding="utf-8")
gate = (MAC / "MacGateActivity.java").read_text(encoding="utf-8")
heartbeat = (MAC / "MacPanelHeartbeat.java").read_text(encoding="utf-8")

assert '"/api/v5/apps/"' in client
assert '"/config?mac="' in client
assert 'DEFAULT_APP_ID = "nexus"' in client
assert '02:00:00:00:00:00' in provider or 'ANDROID_PLACEHOLDER' in provider
assert 'playlist_urls' in client
assert '/api/v5/heartbeat?mac=' in heartbeat
assert '/api/v5/list-notifications?mac=' in heartbeat
assert 'MacHomeActivity' in gate
assert not re.search(r'setSharedPreference(?:Username|Password)|authentication\s*\(', ''.join(p.read_text(encoding="utf-8") for p in MAC.glob("*.java")))

for manifest_path in MANIFESTS:
    xml = manifest_path.read_text(encoding="utf-8")
    ET.fromstring(xml)
    assert xml.count('android:name="com.xplay.ibotv.mac.MacGateActivity"') == 1
    assert 'android:name="com.xplay.ibotv.mac.MacHomeActivity"' in xml
    main_block = re.search(r'<activity\s+[^>]*android:name="com.xplay.ibotv.MainActivity".*?</activity>|<activity\s+[^>]*/>', xml, re.S)
    assert 'android:name="com.xplay.ibotv.MainActivity"' in xml
    assert xml.count('android.intent.category.LAUNCHER') == 1
    assert xml.count('android.intent.category.LEANBACK_LAUNCHER') == 1

for path in ROOT.rglob("*"):
    if path.is_file() and ".git" not in path.parts:
        if path.suffix.lower() in {".apk", ".aab", ".so"}:
            raise AssertionError(f"binário proibido: {path}")
        text = path.read_bytes()
        if path.name != "validate_mac_patch.py" and b"github_pat_" in text:
            raise AssertionError(f"token GitHub encontrado: {path}")

print("MAC patch validation: OK")
