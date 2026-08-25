# Build de teste v3

A v3 amplia a descoberta automática do MAC. Além de `WifiInfo` e `NetworkInterface`, ela tenta ler os arquivos de endereço das interfaces candidatas em `/sys/class/net`, como `wlan0`, `eth0`, `wifi0` e equivalentes. O valor encontrado é normalizado para `AA:BB:CC:DD:EE:FF` e exibido no campo da tela antes da consulta ao painel.

| Campo | Valor |
|---|---|
| Pacote | `com.tobi.playx` |
| Versão exibida | 4.3 |
| Launcher | `com.xplay.ibotv.mac.MacGateActivity` |
| Rota | `https://renciaapp.manus.space/api/v5/apps/nexus/config?mac={MAC}` |
| Diagnóstico | Mostra o MAC detectado ou informa que nenhuma interface o disponibilizou |
| Fallback | Campo para informar o MAC do dispositivo sem usuário e senha |
| Assinatura | V1/V2/V3 válida com chave local de teste |
| SHA-256 | `08541c14953135614179bd922cedc66a228448329bf3300dfbcc62fb717131ff` |
| Teste dinâmico | Não realizado com ADB nesta sessão |

A v3 é um APK de teste e não pode atualizar a instalação original porque usa uma assinatura local diferente. Se o Android do aparelho negar a leitura de `/sys/class/net`, o campo continuará disponível para exibir/receber o MAC obtido nas configurações do sistema.
