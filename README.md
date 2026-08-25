# APK1 — análise estática do Tobi Play 4.3

Este repositório privado contém uma análise estática do arquivo `TobiPlay4.3.apk`, realizada sem instalar ou executar o aplicativo. O objetivo é preservar evidências técnicas úteis — manifesto, metadados, fontes Java descompiladas, recursos textuais, inventário e indicadores — sem redistribuir o binário original nem as bibliotecas nativas extraídas.

> **Aviso de autorização.** O conteúdo deve permanecer privado e ser utilizado somente se você tiver autorização para analisar e redistribuir os artefatos derivados. A descompilação pode estar sujeita a direitos autorais, licença de software, termos de serviço e legislação aplicável.

## Identificação

| Campo | Valor |
|---|---|
| Arquivo analisado | `TobiPlay4.3.apk` |
| Pacote Android | `com.tobi.playx` |
| Nome exibido | `Tobi Play` |
| Versão | `4.3` / código `52` |
| SDK mínimo / alvo | `21` / `36` |
| Atividade inicial | `com.xplay.ibotv.MainActivity` |
| SHA-256 do APK original | `71e399d32221cbb6a548928a00c6cde4f8e891fb44f6fa4197c9bb9c9b9a93df` |

A identificação completa está em [`analysis/apk_metadata.json`](analysis/apk_metadata.json) e o manifesto decodificado em [`analysis/AndroidManifest.xml`](analysis/AndroidManifest.xml).

## Achados de alto nível

O APK possui três arquivos DEX, totalizando **15.901 classes**, além de 20 bibliotecas nativas distribuídas entre `arm64-v8a`, `armeabi-v7a`, `x86` e `x86_64`. A aplicação implementa um cliente de TV/IPTV com telas para conteúdo ao vivo, filmes, séries, EPG, reprodução, legendas e seleção de playlist; o contrato Retrofit expõe chamadas Xtream com `player_api.php`, `xmltv.php`, categorias, streams e informações de VOD/séries [`decompiled/sources/com/xplay/ibotv/remote/APIService.java`](decompiled/sources/com/xplay/ibotv/remote/APIService.java).

Foram observados riscos que merecem revisão antes de qualquer uso em produção: `android:usesCleartextTraffic="true"`, permissões de armazenamento externo e escrita de configurações, persistência de servidor/usuário/senha em `SharedPreferences`, montagem de URLs com credenciais na query string, um cliente TLS permissivo embarcado e um canal de atualização que pode baixar e iniciar a instalação de um APK. A presença de uma classe insegura no artefato não prova, por si só, que ela é chamada em todos os fluxos; o relatório separa evidência de inferência [`docs/relatorio.md`](docs/relatorio.md).

## Estrutura

| Caminho | Conteúdo |
|---|---|
| `analysis/` | Manifesto, metadados, inventário, hashes e varreduras estáticas sanitizadas |
| `decompiled/sources/` | Fontes Java geradas pelo JADX, incluindo dependências embarcadas |
| `decompiled/resources/` | Recursos textuais decodificados, XML e HTML |
| `scripts/` | Script reprodutível de coleta de metadados |
| `docs/relatorio.md` | Relatório técnico detalhado, evidências e limitações |

O APK original, as bibliotecas `.so`, o diretório de ferramentas e arquivos de log volumosos ficam fora da publicação. A chave literal encontrada no código foi substituída por `<REDACTED_TMDB_API_KEY>` na cópia publicada; os valores de configuração nativa continuam representados apenas como dados obfuscados, sem tentativa de executar código nativo.

## Reprodução local

O script [`scripts/analyze_apk.py`](scripts/analyze_apk.py) requer Python 3 e Androguard. Para reproduzir a coleta em um ambiente autorizado, execute `python3 scripts/analyze_apk.py caminho/para/TobiPlay4.3.apk analysis/apk_metadata.json`. A descompilação Java foi feita com JADX 1.5.6; o resultado é uma reconstrução aproximada e não constitui um projeto Android compilável.

## Referências

[1]: analysis/apk_metadata.json "Metadados estáticos do APK"
[2]: analysis/AndroidManifest.xml "Manifesto Android decodificado"
[3]: decompiled/sources/com/xplay/ibotv/remote/APIService.java "Contrato Retrofit da aplicação"
[4]: docs/relatorio.md "Relatório técnico detalhado"
[5]: analysis/inventory.txt "Inventário e hashes dos artefatos"
