$recipesDir = "D:\MinecraftModsDev\HammersAndTools\src\main\resources\data\hammersandtools\recipes"

function Save-Recipe($filename, $jsonContent) {
    $path = Join-Path $recipesDir "$filename.json"
    Set-Content -Path $path -Value $jsonContent -Encoding UTF8
    Write-Host "Generada receta: $filename.json"
}

# 1. RECETAS DE NÚCLEOS
# NV2: reinforced_core (Centro: mining_core, 4 obsidianas en cruz, 4 bloques de redstone en esquinas)
$reinforcedCore = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "RSR",
    "SMS",
    "RSR"
  ],
  "key": {
    "R": {
      "item": "minecraft:redstone_block"
    },
    "S": {
      "item": "minecraft:obsidian"
    },
    "M": {
      "item": "hammersandtools:mining_core"
    }
  },
  "result": {
    "item": "hammersandtools:reinforced_core",
    "count": 1
  }
}
"@
Save-Recipe "reinforced_core" $reinforcedCore

# NV3: advanced_core (Centro: reinforced_core, 4 diamantes en cruz, 4 perlas de ender en esquinas)
$advancedCore = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "EDE",
    "DRD",
    "EDE"
  ],
  "key": {
    "E": {
      "item": "minecraft:ender_pearl"
    },
    "D": {
      "item": "minecraft:diamond"
    },
    "R": {
      "item": "hammersandtools:reinforced_core"
    }
  },
  "result": {
    "item": "hammersandtools:advanced_core",
    "count": 1
  }
}
"@
Save-Recipe "advanced_core" $advancedCore

# NV4: superior_core (Centro: advanced_core, 4 bloques de diamante en esquinas, 4 fragmentos de netherite en cruz)
$superiorCore = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "BNB",
    "NAN",
    "BNB"
  ],
  "key": {
    "B": {
      "item": "minecraft:diamond_block"
    },
    "N": {
      "item": "minecraft:netherite_scrap"
    },
    "A": {
      "item": "hammersandtools:advanced_core"
    }
  },
  "result": {
    "item": "hammersandtools:superior_core",
    "count": 1
  }
}
"@
Save-Recipe "superior_core" $superiorCore

# 2. RECETAS DE MARTILLOS NIVEL 1
# Patrón en forma de gran martillo:
# MMM
# MCM
#  S 
$materialsT1 = @{
    "stone_hammer" = @{ "ing" = "minecraft:cobblestone"; "stick" = "minecraft:stick" }
    "iron_hammer" = @{ "ing" = "minecraft:iron_ingot"; "stick" = "minecraft:stick" }
    "gold_hammer" = @{ "ing" = "minecraft:gold_ingot"; "stick" = "minecraft:stick" }
    "diamond_hammer" = @{ "ing" = "minecraft:diamond"; "stick" = "minecraft:stick" }
}

foreach ($h in $materialsT1.Keys) {
    $mat = $materialsT1[$h]
    $recipeT1 = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "MMM",
    "MCM",
    " S "
  ],
  "key": {
    "M": {
      "item": "$($mat.ing)"
    },
    "C": {
      "item": "hammersandtools:mining_core"
    },
    "S": {
      "item": "$($mat.stick)"
    }
  },
  "result": {
    "item": "hammersandtools:$h",
    "count": 1
  }
}
"@
    Save-Recipe $h $recipeT1
}

# Netherite Hammer T1: en mesa de crafteo consumiendo diamond_hammer + lingote de netherite + mining_core
$netheriteT1 = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    " N ",
    "CHC",
    " S "
  ],
  "key": {
    "N": {
      "item": "minecraft:netherite_ingot"
    },
    "H": {
      "item": "hammersandtools:diamond_hammer"
    },
    "C": {
      "item": "hammersandtools:mining_core"
    },
    "S": {
      "item": "minecraft:netherite_scrap"
    }
  },
  "result": {
    "item": "hammersandtools:netherite_hammer",
    "count": 1
  }
}
"@
Save-Recipe "netherite_hammer" $netheriteT1

# 3. RECETAS DE MEJORA A NIVEL 2
# Requiere el martillo T1 del mismo material + reinforced_core + 2 bloques/lingotes del material:
#  M 
# RHC
#  M 
$materialsT2 = @{
    "stone_hammer_t2" = @{ "base" = "stone_hammer"; "reinforce" = "minecraft:smooth_stone" }
    "iron_hammer_t2" = @{ "base" = "iron_hammer"; "reinforce" = "minecraft:iron_block" }
    "gold_hammer_t2" = @{ "base" = "gold_hammer"; "reinforce" = "minecraft:gold_block" }
    "diamond_hammer_t2" = @{ "base" = "diamond_hammer"; "reinforce" = "minecraft:diamond_block" }
    "netherite_hammer_t2" = @{ "base" = "netherite_hammer"; "reinforce" = "minecraft:netherite_ingot" }
}

foreach ($h2 in $materialsT2.Keys) {
    $info = $materialsT2[$h2]
    $recipeT2 = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    " M ",
    "RHC",
    " M "
  ],
  "key": {
    "M": {
      "item": "$($info.reinforce)"
    },
    "R": {
      "item": "hammersandtools:reinforced_core"
    },
    "H": {
      "item": "hammersandtools:$($info.base)"
    },
    "C": {
      "item": "minecraft:crying_obsidian"
    }
  },
  "result": {
    "item": "hammersandtools:$h2",
    "count": 1
  }
}
"@
    Save-Recipe $h2 $recipeT2
}

Write-Host "Todas las recetas JSON fueron generadas exitosamente."
