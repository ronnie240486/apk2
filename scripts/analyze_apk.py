#!/usr/bin/env python3
import hashlib
import json
import os
import sys
import zipfile
from collections import Counter
from pathlib import Path

from androguard.core.apk import APK
from androguard.core.dex import DEX


def safe(callable_, default=None):
    try:
        value = callable_()
        if value is None:
            return default
        return value
    except Exception as exc:
        return {"error": f"{type(exc).__name__}: {exc}"}


def serializable(value):
    if isinstance(value, (str, int, float, bool)) or value is None:
        return value
    if isinstance(value, dict):
        return {str(k): serializable(v) for k, v in value.items()}
    if isinstance(value, (list, tuple, set)):
        return [serializable(v) for v in value]
    return str(value)


def main():
    if len(sys.argv) != 3:
        raise SystemExit(f"uso: {sys.argv[0]} APK SAIDA_JSON")
    apk_path = Path(sys.argv[1]).resolve()
    out_path = Path(sys.argv[2]).resolve()
    sha = hashlib.sha256()
    with apk_path.open("rb") as fh:
        for block in iter(lambda: fh.read(1024 * 1024), b""):
            sha.update(block)

    with zipfile.ZipFile(apk_path) as zf:
        entries = zf.infolist()
        top_dirs = Counter((e.filename.split("/", 1)[0] for e in entries if e.filename))
        dex_files = [e.filename for e in entries if e.filename.endswith(".dex")]
        native_libs = [e.filename for e in entries if e.filename.startswith("lib/") and e.filename.endswith(".so")]
        assets = [e.filename for e in entries if e.filename.startswith("assets/")]
        resources = [e.filename for e in entries if e.filename.startswith("res/")]
        meta_inf = [e.filename for e in entries if e.filename.startswith("META-INF/")]

    apk = APK(str(apk_path))
    dexes = []
    for dex_name, dex_bytes in zip(apk.get_dex_names(), apk.get_all_dex()):
        try:
            dexes.append((dex_name, DEX(dex_bytes)))
        except Exception as exc:
            dexes.append((dex_name, {"error": f"{type(exc).__name__}: {exc}"}))

    activities = safe(apk.get_activities, [])
    services = safe(apk.get_services, [])
    receivers = safe(apk.get_receivers, [])
    providers = safe(apk.get_providers, [])
    permissions = safe(apk.get_permissions, [])
    features = safe(apk.get_features, [])
    package_name = safe(apk.get_package)
    main_activity = safe(apk.get_main_activity)

    # Keep only stable certificate metadata; do not export raw certificate blobs.
    certs = []
    for cert in safe(apk.get_certificates, []) or []:
        certs.append({
            "issuer": serializable(getattr(getattr(cert, "issuer", None), "native", "")),
            "subject": serializable(getattr(getattr(cert, "subject", None), "native", "")),
            "serial": str(getattr(cert, "serial_number", "")),
            "sha256": safe(lambda: cert.sha256.hex(), None),
            "not_before": str(getattr(cert, "not_valid_before", "")),
            "not_after": str(getattr(cert, "not_valid_after", "")),
        })

    result = {
        "file": {
            "name": apk_path.name,
            "size_bytes": apk_path.stat().st_size,
            "sha256": sha.hexdigest(),
        },
        "application": {
            "package": package_name,
            "label": safe(apk.get_app_name),
            "version_name": safe(apk.get_androidversion_name),
            "version_code": safe(apk.get_androidversion_code),
            "min_sdk": safe(apk.get_min_sdk_version),
            "target_sdk": safe(apk.get_target_sdk_version),
            "main_activity": main_activity,
        },
        "components": {
            "activities": serializable(activities),
            "services": serializable(services),
            "receivers": serializable(receivers),
            "providers": serializable(providers),
            "counts": {
                "activities": len(activities) if isinstance(activities, list) else None,
                "services": len(services) if isinstance(services, list) else None,
                "receivers": len(receivers) if isinstance(receivers, list) else None,
                "providers": len(providers) if isinstance(providers, list) else None,
            },
        },
        "permissions": serializable(permissions),
        "features": serializable(features),
        "certificates": certs,
        "archive": {
            "entry_count": len(entries),
            "dex_files": dex_files,
            "native_libs": native_libs,
            "assets": assets,
            "resource_count": len(resources),
            "meta_inf": meta_inf,
            "top_level_entries": dict(top_dirs),
        },
        "dex": {
            "count": len(dexes),
            "classes": [],
        },
    }

    for dex_name, dex in dexes:
        if isinstance(dex, dict):
            result["dex"]["classes"].append({"name": dex_name, **dex})
            continue
        classes = []
        try:
            for cls in dex.get_classes():
                classes.append(cls.get_name())
        except Exception as exc:
            classes = [{"error": f"{type(exc).__name__}: {exc}"}]
        result["dex"]["classes"].append({
            "name": dex_name,
            "class_count": len(classes),
            "classes": classes,
        })

    out_path.parent.mkdir(parents=True, exist_ok=True)
    out_path.write_text(json.dumps(serializable(result), ensure_ascii=False, indent=2), encoding="utf-8")
    print(json.dumps({"output": str(out_path), "package": package_name, "dex_count": len(dexes)}, ensure_ascii=False))


if __name__ == "__main__":
    main()


def _unused():
    return os.name
