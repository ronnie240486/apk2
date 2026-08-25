# Artefatos da análise

Esta pasta reúne os resultados derivados da análise estática do APK. Os arquivos foram gerados a partir do `TobiPlay4.3.apk` fornecido nesta tarefa e não foram obtidos por execução do aplicativo.

| Arquivo | Finalidade |
|---|---|
| `apk_metadata.json` | Identidade, SDKs, componentes, permissões, certificados, DEXs, classes e composição do ZIP |
| `AndroidManifest.xml` | Manifesto binário decodificado |
| `inventory.txt` | Contagens, pacotes principais e hashes dos relatórios |
| `code_indicators.txt` | Indicadores de rede, WebView, armazenamento e autenticação encontrados nas fontes |
| `url_scan.txt` | Varredura de URLs e referências de serviço |
| `native_indicators.txt` | Arquiteturas, BuildIDs e indícios das bibliotecas nativas |
| `secret_scan.txt` | Varredura preliminar de padrões de credenciais e chaves |

A publicação exclui deliberadamente o APK original, as bibliotecas `.so`, os arquivos de ferramenta e os logs extensos. Uma chave literal de serviço de metadados foi mascarada na fonte Java publicada como `<REDACTED_TMDB_API_KEY>`. As sequências `ScKit-*`, bytes obfuscados e métodos `native` são mantidos como evidência estrutural, não como segredos decodificados.

Os artefatos representam um snapshot desta versão específica. O hash SHA-256 do APK original é `71e399d32221cbb6a548928a00c6cde4f8e891fb44f6fa4197c9bb9c9b9a93df`; mudanças no APK exigem uma nova análise e novos hashes.
