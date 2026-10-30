$modelsDir = "D:\MinecraftModsDev\HammersAndTools\src\main\resources\assets\hammersandtools\models\item"

$items = @(
    "mining_core",
    "reinforced_core",
    "advanced_core",
    "superior_core",
    "tree_core",
    "excavation_core",
    "stone_hammer",
    "iron_hammer",
    "gold_hammer",
    "diamond_hammer",
    "netherite_hammer",
    "stone_hammer_t2",
    "iron_hammer_t2",
    "gold_hammer_t2",
    "diamond_hammer_t2",
    "netherite_hammer_t2",
    "stone_hammer_t3",
    "iron_hammer_t3",
    "gold_hammer_t3",
    "diamond_hammer_t3",
    "netherite_hammer_t3",
    "stone_hammer_t4",
    "iron_hammer_t4",
    "gold_hammer_t4",
    "diamond_hammer_t4",
    "netherite_hammer_t4"
)

foreach ($item in $items) {
    $json = @"
{
  "parent": "minecraft:item/handheld",
  "textures": {
    "layer0": "hammersandtools:item/$item"
  }
}
"@
    # Si es núcleo, usamos item/generated; si es martillo, item/handheld para que se sostenga como herramienta
    if ($item -like "*core*") {
        $json = @"
{
  "parent": "minecraft:item/generated",
  "textures": {
    "layer0": "hammersandtools:item/$item"
  }
}
"@
    }
    $path = Join-Path $modelsDir "$item.json"
    Set-Content -Path $path -Value $json -Encoding UTF8
}

Write-Host "Modelos JSON generados exitosamente."
