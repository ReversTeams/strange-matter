"""Regression checks for Minecraft 1.21.1 resource migration."""
import json
from pathlib import Path
root = Path(__file__).resolve().parents[1] / "src/main/resources"
checked = 0
for path in root.rglob("*.json"):
    data = json.loads(path.read_text(encoding="utf-8"))
    def check(value):
        if isinstance(value, dict):
            for key, entry in value.items():
                if key in ("type", "condition", "loader") and isinstance(entry, str):
                    assert not entry.startswith(("forge:", "c:")), (path, key, entry)
                check(entry)
        elif isinstance(value, list):
            for entry in value: check(entry)
    check(data)
    if "recipe" in path.parts and isinstance(data, dict) and "result" in data:
        assert isinstance(data["result"], dict) and "id" in data["result"], path
    if "data" in path.parts:
        assert "forge" not in path.parts, path
        assert not set(("recipes", "advancements", "loot_tables", "functions")).intersection(path.parts), path
    checked += 1
assert (root / "kubejs.plugins.txt").is_file()
print(f"Validated {checked} JSON resources, recipe results, registry paths, and plugin descriptor")
