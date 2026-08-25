# Relatório técnico — Tobi Play 4.3

## 1. Escopo e método

A análise foi conduzida sobre `TobiPlay4.3.apk` em modo exclusivamente estático. O arquivo não foi instalado, aberto, conectado a servidores, submetido a emulador nem executado; portanto, este documento descreve o que pode ser observado no contêiner APK, no manifesto, nos DEXs, nas bibliotecas nativas e no código Java reconstruído.

O inventário foi produzido com Androguard e a reconstrução Java com JADX 1.5.6. O resultado do JADX é uma aproximação: classes com lógica nativa, símbolos removidos, controle de fluxo obfuscado ou dependências incompletas não devem ser tratados como código-fonte original. Os metadados brutos estão em [`analysis/apk_metadata.json`](../analysis/apk_metadata.json), e o inventário de artefatos está em [`analysis/inventory.txt`](../analysis/inventory.txt).

## 2. Identidade e composição

| Propriedade | Resultado |
|---|---|
| Nome do arquivo | `TobiPlay4.3.apk` |
| Tamanho | 28.050.096 bytes |
| SHA-256 | `71e399d32221cbb6a548928a00c6cde4f8e891fb44f6fa4197c9bb9c9b9a93df` |
| Pacote | `com.tobi.playx` |
| Nome exibido | `Tobi Play` |
| Versão | `4.3`, código `52` |
| SDK mínimo / alvo | `21` / `36` |
| Atividade launcher | `com.xplay.ibotv.MainActivity` |
| Atividade para Leanback/TV | `com.xplay.ibotv.MainTVActivity` |
| Classes nos DEXs | `6.419` + `6.143` + `3.339` = **15.901** |
| Entradas no arquivo ZIP | `1.667` |
| Bibliotecas nativas | `20`, em quatro ABIs |

O APK contém `classes.dex`, `classes2.dex` e `classes3.dex`, recursos Android, cinco arquivos em `META-INF`, assets HTML de anúncios e modo offline, além de bibliotecas para o módulo proprietário `boxbr`, FFmpeg, GIF e Realm. Os detalhes verificáveis estão no inventário JSON e na listagem produzida durante a análise [`analysis/apk_metadata.json`](../analysis/apk_metadata.json).

## 3. Manifesto, componentes e permissões

O manifesto decodificado declara 32 Activities, dois Services, dois Providers e nenhum BroadcastReceiver próprio. Apenas `MainActivity` e `MainTVActivity` aparecem como Activities exportadas com filtros `MAIN`/`LAUNCHER` ou `LEANBACK_LAUNCHER`; as demais Activities do aplicativo estão marcadas como não exportadas. O serviço `AssetPackExtractionService` aparece como exportado, porém desabilitado. O `FileProvider` está não exportado, com concessão de URI habilitada e autoridade `com.tobi.playx.provider` [`analysis/AndroidManifest.xml`](../analysis/AndroidManifest.xml).

| Categoria | Itens observados | Leitura técnica |
|---|---|---|
| Rede | `INTERNET`, `ACCESS_NETWORK_STATE`, `ACCESS_WIFI_STATE` | Compatível com carregamento de portal, catálogo, imagens e EPG |
| Armazenamento | `READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE`, `requestLegacyExternalStorage=true` | Aumenta a superfície de acesso a arquivos em versões antigas do Android |
| Execução/energia | `WAKE_LOCK`, `FOREGROUND_SERVICE`, `DISABLE_KEYGUARD` | Compatível com reprodução contínua e uso em TV |
| Configuração sensível | `WRITE_SETTINGS` | Permissão especial; o uso efetivo precisa ser confirmado dinamicamente |
| Hardware anunciado | câmera, microfone, localização GPS, NFC, telefonia, sensor, Wi-Fi | Todas as features estão marcadas como não obrigatórias no manifesto |
| Transporte | `android:usesCleartextTraffic="true"` | Permite tráfego HTTP em claro; deve ser tratado como achado de segurança |
| Backup | `android:allowBackup="true"` | Pode permitir mecanismos de backup conforme as políticas do dispositivo |
| Compatibilidade | `android:largeHeap="true"`, `supportsRtl`, biblioteca `org.apache.http.legacy` | Indicadores de compatibilidade e consumo elevado de memória |

O `FileProvider` aponta para um `<external-path path="."/>`, configuração ampla para o armazenamento externo. Embora o Provider esteja marcado como não exportado, os URIs concedidos a outros aplicativos devem ser revisados em conjunto com cada fluxo de compartilhamento [`decompiled/resources/decompiled/java/resources/res/xml/provider_paths.xml`](../decompiled/resources/res/xml/provider_paths.xml).

## 4. Arquitetura funcional observada

Os nomes de pacotes e classes indicam uma aplicação de televisão/IPTV com três fluxos principais: conteúdo ao vivo, vídeo sob demanda e séries. Há Activities para categorias, busca, informações de filmes e séries, reprodução de filmes e episódios, canais ao vivo, catch-up, configurações, seleção de playlist e telas mobile/TV. O contrato Retrofit declara `player_api.php`, `xmltv.php`, consultas de categorias, streams, EPG, dados de VOD e informações de séries [`decompiled/sources/com/xplay/ibotv/remote/APIService.java`](../decompiled/sources/com/xplay/ibotv/remote/APIService.java).

| Camada | Evidência | Interpretação |
|---|---|---|
| Bootstrap | `MainActivity`, `LTVApp` e chamadas `native` | A inicialização e parte da validação estão delegadas a código nativo |
| Conta/portal | `BxLoginActivity`, `BxCheckUserTask`, `BxValidateLoginTask` | Há suporte a credenciais Xtream/XUI/M3U e validação de acesso |
| Catálogo | `APIService`, modelos de filmes, séries, canais e categorias | Consumo de APIs de catálogo e portal com objetos JSON |
| Reprodução | Activities de player e FFmpeg nativo | Reprodução de fluxos e suporte multimídia |
| Legendas | `GetSubtitle*Request` e `Constants` | Serviço secundário com API key e Bearer token |
| Atualização | `SettingActivity`, `versionUpdate`, `FileProvider` | Canal de atualização dependente do `apk_link` remoto |
| Branding/configuração | `AppInfoModel`, logo, background, temas e lista de URLs | Configuração visual e portais parecem ser recebidos do backend |

A classe `AppInfoModel` inclui campos como `agent`, `apikey`, `apk_link`, `app_version`, `bg`, `device_key`, `expire_date`, `logo`, `mac_address`, `pin`, `plan_id`, `price`, `theme`, `ads` e `urls`. A lista `urls` representa entradas de portal/playlist com tipo, nome, proteção e endereço. Esta estrutura sugere que parte relevante do comportamento é orientada por configuração remota, não por endpoints fixos legíveis no Java.

## 5. Credenciais e armazenamento local

A classe `PreferenceHelper` usa `getSharedPreferences("PREF", 0)` e grava diretamente campos como `server_url`, `user_name`, `password` e `user_id`. Também serializa modelos de login e configurações de legendas com Gson. Os métodos `setSharedPreferencePassword`, `setSharedPreferenceUsername`, `setSharedPreferenceServerUrl` e `setSharedPreferenceLoginModel` evidenciam persistência em texto no armazenamento privado da aplicação [`decompiled/sources/com/xplay/ibotv/helper/PreferenceHelper.java`](../decompiled/sources/com/xplay/ibotv/helper/PreferenceHelper.java).

O módulo Bx mantém uma segunda preferência chamada `BxLoginPref`. `HomeActivity.copyBxLoginToXp` copia `BX.USERNAME` e `BX.PASSWORD` para `XpLoginPref`; depois, a mesma Activity monta `BoxBRURL` usando `BX_AUTH_URL`, nome de usuário e senha persistidos. Isso significa que credenciais de acesso ao portal podem permanecer armazenadas e ser reutilizadas por múltiplos fluxos do aplicativo [`decompiled/sources/com/xplay/ibotv/activities/HomeActivity.java`](../decompiled/sources/com/xplay/ibotv/activities/HomeActivity.java).

Há ainda um problema de exposição durante a comunicação: a implementação de fallback em `SeasonActivity` concatena `username`, `password` e `series_id` diretamente na URL de `player_api.php`. Mesmo com HTTPS, credenciais em query string podem aparecer em logs, proxies, histórico de requisições ou telemetria intermediária; com HTTP em claro permitido pelo manifesto, o impacto potencial é maior [`decompiled/sources/com/xplay/ibotv/activities/SeasonActivity.java`](../decompiled/sources/com/xplay/ibotv/activities/SeasonActivity.java).

## 6. Transporte e TLS

`RetroClass` cria um cliente OkHttp com `HttpLoggingInterceptor.Level.BODY`, o que habilita logging do corpo das requisições no fluxo em que esse cliente é utilizado. A mesma classe configura timeouts de 60 segundos e usa Retrofit com conversores Gson e Scalars [`decompiled/sources/com/xplay/ibotv/remote/RetroClass.java`](../decompiled/sources/com/xplay/ibotv/remote/RetroClass.java).

`UnsafeOkHttpClient.getUnsafeOkHttpClient()` cria um `X509TrustManager` cujos métodos de validação não fazem verificações e um `HostnameVerifier` que sempre retorna `true`. A presença dessa implementação representa um risco crítico se ela for usada no caminho real de rede, mas a análise estática não prova que todos os clientes do aplicativo a instanciam. O uso deve ser confirmado por busca de referências e, idealmente, por teste controlado em ambiente isolado [`decompiled/sources/com/xplay/ibotv/utils/UnsafeOkHttpClient.java`](../decompiled/sources/com/xplay/ibotv/utils/UnsafeOkHttpClient.java).

## 7. Obfuscação e código nativo

`Constants` contém strings reconstruídas a partir de índices em uma tabela de caracteres e também chama `EnigmaUtils.enigmatization` para decodificar valores. `EnigmaUtils` delega a operação de descriptografia a `BxAppSecurity.decrypt`, que é um método nativo. A classe `BxAppSecurity` expõe outras funções nativas de decodificação, montagem de dados, validação de assinatura, dados de usuário e verificação de compra [`decompiled/sources/com/xplay/ibotv/apps/Constants.java`](../decompiled/sources/com/xplay/ibotv/apps/Constants.java) [`decompiled/sources/com/xplay/ibotv/utils/EnigmaUtils.java`](../decompiled/sources/com/xplay/ibotv/utils/EnigmaUtils.java) [`decompiled/sources/com/boxbrapks/BxAppSecurity.java`](../decompiled/sources/com/boxbrapks/BxAppSecurity.java).

As classes Bx de login e validação possuem grande quantidade de métodos `native`, incluindo validação de M3U/XC, resolução de portal, teste de stream, coleta de identificador de dispositivo e rotinas de interface. As bibliotecas `libboxbr.so` e `libboxbrpro.so` estão stripped nas quatro ABIs observadas. Por isso, o repositório publica a evidência de presença e os metadados, mas não tenta reconstruir a lógica nativa executando ou carregando as `.so`.

Uma chave literal de serviço de metadados foi encontrada no código descompilado. Para reduzir o risco de redistribuição, o valor foi substituído por `<REDACTED_TMDB_API_KEY>` na cópia destinada ao GitHub. Os payloads obfuscados permanecem como bytes e chamadas nativas, sem decodificação operacional no repositório publicado.

## 8. Assets HTML e serviços externos

Os assets `AdsOne.html`, `AdsTwo.html`, `AdsThree.html`, `AdsFour.html` e `combo.html` implementam telas HTML de publicidade/conteúdo. Um dos arquivos consulta a API do TMDB para conteúdo infantil usando uma chave recebida por parâmetro de URL e carrega imagens de `image.tmdb.org`. Isso cria dependência externa e merece revisão caso os HTMLs sejam exibidos em WebView ou em outro componente que permita navegação ativa [`decompiled/resources/decompiled/java/resources/assets/ads/AdsFour.html`](../decompiled/resources/assets/ads/AdsFour.html).

O serviço de legendas utiliza cabeçalhos `Api-Key` e `Authorization: Bearer ...` em requisições Volley. O relatório não imprime valores completos desses campos. A análise pode afirmar o formato do mecanismo, mas não determinar, apenas a partir dos wrappers, a origem e a validade das credenciais usadas em tempo de execução [`decompiled/sources/com/xplay/ibotv/remote/GetSubtitleLinkRequest.java`](../decompiled/sources/com/xplay/ibotv/remote/GetSubtitleLinkRequest.java).

## 9. Atualização automática e instalação

Em dispositivos Amazon, `SettingActivity.goToUpdate()` passa `appInfoModel.getApk_link()` para uma tarefa de atualização. Em outros dispositivos, a Activity abre a Play Store ou o endereço correspondente. Após o download, `startInstall(File)` cria um Intent com MIME `application/vnd.android.package-archive` e usa o `FileProvider` em Android posterior ao 7.0. A origem, autenticidade, assinatura e integridade do APK de atualização devem ser validadas antes da instalação; este relatório não realizou download nem instalação [`decompiled/sources/com/xplay/ibotv/activities/SettingActivity.java`](../decompiled/sources/com/xplay/ibotv/activities/SettingActivity.java).

## 10. Matriz de riscos e recomendações

| Severidade | Achado | Confiança | Recomendação |
|---|---|---|---|
| Crítica, condicional | Trust manager que aceita qualquer certificado e hostname verifier sempre verdadeiro | Alta quanto à presença; condicional quanto ao uso | Remover o cliente inseguro e aplicar validação padrão de cadeia e hostname |
| Alta | Credenciais persistidas em `SharedPreferences` e duplicadas entre preferências | Alta | Usar armazenamento protegido, tokens de curta duração e limpeza explícita no logout |
| Alta | Credenciais inseridas na query string do fallback | Alta | Migrar para headers/body seguro e impedir logging de corpos com segredos |
| Alta | `usesCleartextTraffic=true` | Alta | Desabilitar HTTP em produção e aplicar Network Security Config restritiva |
| Alta, condicional | Atualização via `apk_link` remoto seguida de instalação | Alta quanto ao caminho; condicional quanto à origem | Exigir HTTPS, assinatura conhecida, hash verificado e política explícita de instalação |
| Média | Logging de corpo em Retrofit | Alta | Desabilitar `BODY` em builds de produção e filtrar dados sensíveis |
| Média | `external-path path="."` no FileProvider | Alta | Restringir caminhos ao mínimo necessário e revisar cada concessão URI |
| Média | `allowBackup=true` e armazenamento legado | Alta | Definir política de backup e migrar para armazenamento moderno |
| Baixa/condicional | Chave de API embutida no cliente | Alta quanto à presença histórica; impacto depende do serviço | Revogar/rotacionar a chave e aplicar restrições de origem/cota no provedor |

## 11. Implementação MAC → painel

Com base no PDF fornecido, o projeto agora contém um adaptador para a rota universal `GET /api/v5/apps/{appId}/config?mac={MAC}`, usando a base `https://renciaapp.manus.space`. O `appId` inicial é `nexus`, porque esse é o valor usado no exemplo do documento; antes de distribuir o APK, ele deve ser alterado para a família efetivamente cadastrada no painel em `MacPanelClient.DEFAULT_APP_ID` ou na preferência `MAC_PANEL_APP_ID` [`docs/main-3-routes.pdf`](../docs/main-3-routes.pdf).

A sequência implementada é: obter e normalizar o identificador disponível, chamar a configuração, interromper o fluxo quando `registered` ou `allowed` forem falsos, transformar `playlist_urls` em `AppInfoModel`, carregar a primeira playlist pelo pipeline M3U existente e abrir a Home. A Activity de entrada não contém campos de usuário ou senha. O heartbeat e a consulta de avisos são iniciados após o carregamento, com intervalo de 60 segundos; também foram incluídos métodos para `/api/v5/playback-failure`, `/api/v5/list-notifications/ack` e `/api/v5/remote-commands/ack`.

O manifesto publicado troca os filtros de launcher das Activities legadas por `com.xplay.ibotv.mac.MacGateActivity`. A alteração é deliberadamente um patch sobre código descompilado, não um APK pronto: a lógica nativa original, o SDK Android, as dependências e a assinatura legítima não estão disponíveis nesta análise. Para gerar um APK instalável, o patch precisa ser integrado ao projeto-fonte Android e assinado com a chave autorizada.

O Android 10 ou superior restringe o acesso de aplicativos comuns ao MAC físico/factory MAC e usa randomização por padrão em conexões Wi-Fi. O AOSP também documenta que MACs persistentes ou não persistentes podem variar conforme o perfil de rede e a versão do sistema [10] [11]. Por isso, a implementação rejeita placeholders como `02:00:00:00:00:00` e exibe falha de cadastro quando nenhum MAC utilizável está disponível; em TV Boxes gerenciadas, deve ser validado qual identificador o fabricante expõe.

## 12. Limitações

Não foram realizados testes dinâmicos, captura de tráfego, instrumentação, análise de memória, execução das bibliotecas nativas, validação de login, verificação de assinatura no Android ou confirmação de quais URLs são devolvidas pela configuração remota. Também não é possível inferir propriedade, intenção, licenciamento ou legitimidade do aplicativo somente pelo conteúdo do APK.

A fonte Java descompilada deve ser tratada como material de investigação. Ela pode conter artefatos do descompilador, nomes sintéticos, código de bibliotecas de terceiros e métodos `native` sem corpo. O repositório não é um projeto Android recompilável e não deve ser usado para redistribuição ou publicação pública sem revisão jurídica e autorização do titular.

## Referências locais e externas

[9]: ../docs/main-3-routes.pdf "Contrato de rotas fornecido pelo usuário"
[10]: https://source.android.com/docs/core/permissions/immutable-device-ids "AOSP — Immutable device IDs"
[11]: https://source.android.com/docs/core/connect/wifi-mac-randomization-behavior "AOSP — MAC randomization behavior"

## Referências locais

[1]: ../analysis/apk_metadata.json "Metadados estáticos e inventário do APK"
[2]: ../analysis/AndroidManifest.xml "Manifesto Android decodificado"
[3]: ../decompiled/sources/com/xplay/ibotv/remote/APIService.java "Contrato Retrofit"
[4]: ../decompiled/sources/com/xplay/ibotv/helper/PreferenceHelper.java "Persistência local"
[5]: ../decompiled/sources/com/xplay/ibotv/remote/RetroClass.java "Cliente Retrofit e logging"
[6]: ../decompiled/sources/com/xplay/ibotv/utils/UnsafeOkHttpClient.java "Cliente TLS permissivo"
[7]: ../decompiled/sources/com/xplay/ibotv/activities/SettingActivity.java "Atualização e instalação"
[8]: ../analysis/inventory.txt "Inventário dos artefatos publicados"
