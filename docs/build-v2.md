# Build de teste v2

A versão `TobiPlay4.3-mac-test-v2.apk` foi reconstruída a partir do APK decodificado e assinada localmente com uma chave de teste.

| Campo | Valor |
|---|---|
| Pacote | `com.tobi.playx` |
| Versão exibida | 4.3 |
| Launcher | `com.xplay.ibotv.mac.MacGateActivity` |
| Rota | `https://renciaapp.manus.space/api/v5/apps/nexus/config?mac={MAC}` |
| Correção | HTTP 404 com `registered=false` agora é tratado como resposta de negócio; o gate mostra `MAC não cadastrado.` |
| Fallback | Campo para MAC manual quando o Android não fornece o MAC automaticamente |
| Assinatura | V1/V2/V3 válida com chave local de teste |
| SHA-256 | `4d49eaef1180f0be0f8d7c8129dd07c3c16d2e3d21a0a1a4387581b32a1dd5a1` |
| Teste dinâmico | Não realizado; não havia dispositivo Android conectado |

O APK não é uma atualização compatível com a instalação original porque usa uma assinatura de teste. Para uso real, altere o `appId` se necessário, cadastre o MAC no painel, integre o patch no projeto Android original e assine com a chave legítima.
