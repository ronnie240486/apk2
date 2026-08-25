# Integração por MAC

## Objetivo

O fluxo substitui o formulário de usuário e senha por uma consulta ao painel usando o MAC disponível no dispositivo. O APK não deve gerar, guardar ou enviar credenciais de login para este fluxo.

## Configuração

O adaptador usa inicialmente:

```text
BASE_URL = https://renciaapp.manus.space
APP_ID = nexus
```

Antes da compilação, altere `MacPanelClient.DEFAULT_APP_ID` para a família cadastrada no painel, caso o APK não seja `nexus`. Os valores aceitos no documento de rotas são `prestige`, `optimus`, `imperio`, `infinitus`, `supremus`, `evolux` e `nexus`.

## Primeira chamada

```http
GET https://renciaapp.manus.space/api/v5/apps/nexus/config?mac=AA%3ABB%3ACC%3ADD%3AEE%3AFF
Accept: application/json
```

A resposta deve conter, no mínimo, `registered`, `allowed` e `playlist_urls`. O gate bloqueia o fluxo quando `registered` ou `allowed` forem falsos e também bloqueia quando nenhuma playlist ativa for retornada.

## Ciclo operacional

Depois de carregar a primeira URL de `playlist_urls`, a Home inicia:

```http
GET /api/v5/heartbeat?mac={MAC}&current_content={TITULO}
GET /api/v5/list-notifications?mac={MAC}
```

As duas chamadas são repetidas a cada 60 segundos. `MacPanelHeartbeat` também fornece os POSTs descritos no PDF para falhas de reprodução, confirmação de avisos e confirmação de comandos remotos.

## Limitação do MAC

O Android moderno pode ocultar o MAC físico ou fornecer um MAC randomizado. `MacAddressProvider` rejeita valores nulos, zeros e o placeholder `02:00:00:00:00:00`; ele não inventa identificadores. Em uma TV Box gerenciada, valide no dispositivo real qual interface (`wlan`, `eth` ou equivalente) expõe o MAC aceito pelo painel.

## Compilação e assinatura

Este diretório contém fontes Java descompiladas e um patch de referência. Ele não contém o projeto Gradle original, o Android SDK configurado, os artefatos nativos nem a chave de assinatura. Para gerar o APK, copie as cinco classes do pacote `com.xplay.ibotv.mac`, aplique as entradas do manifesto ao projeto-fonte original, compile com as dependências originais e assine com a chave legítima do aplicativo.

Não distribua um APK recompilado com uma assinatura diferente sem revisar o mecanismo de verificação de assinatura presente no aplicativo.

## Referências

- [Contrato de rotas](main-3-routes.pdf)
- [AOSP — Immutable device IDs](https://source.android.com/docs/core/permissions/immutable-device-ids)
- [AOSP — MAC randomization behavior](https://source.android.com/docs/core/connect/wifi-mac-randomization-behavior)
