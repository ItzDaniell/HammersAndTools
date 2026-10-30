$recipesDir = "D:\MinecraftModsDev\HammersAndTools\src\main\resources\data\hammersandtools\recipes"

function Save-Recipe($filename, $jsonContent) {
    $path = Join-Path $recipesDir "$filename.json"
    Set-Content -Path $path -Value $jsonContent -Encoding UTF8
    Write-Host "Generada receta: $filename.json"
}

# ==============================================================================
# 1. RECETAS DE NÚCLEOS
# - NV1 (mining_core): Minerales básicos en items (Hierro, Cobre, Oro, Redstone, Lapis, Diamante).
# - NV2 (reinforced_core): mining_core + Bloques de minerales de Overworld (Hierro, Redstone, Oro, Lapis, Cobre).
# - NV3 (advanced_core): reinforced_core + Bloques de minerales de ambas dimensiones (Diamante, Esmeralda, Cuarzo, Oro).
# - NV4 (superior_core): advanced_core + Bloques de minerales de máximo nivel de ambas dimensiones (Netherite, Diamante, Cuarzo, Esmeralda).
# ==============================================================================

# NV1: mining_core
$miningCore = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "ICI",
    "RDR",
    "LGL"
  ],
  "key": {
    "I": {
      "item": "minecraft:iron_ingot"
    },
    "C": {
      "item": "minecraft:copper_ingot"
    },
    "R": {
      "item": "minecraft:redstone"
    },
    "D": {
      "item": "minecraft:diamond"
    },
    "L": {
      "item": "minecraft:lapis_lazuli"
    },
    "G": {
      "item": "minecraft:gold_ingot"
    }
  },
  "result": {
    "item": "hammersandtools:mining_core",
    "count": 1
  }
}
"@
Save-Recipe "mining_core" $miningCore

# NV2: reinforced_core
$reinforcedCore = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "IRI",
    "GMG",
    "LCL"
  ],
  "key": {
    "I": {
      "item": "minecraft:iron_block"
    },
    "R": {
      "item": "minecraft:redstone_block"
    },
    "G": {
      "item": "minecraft:gold_block"
    },
    "M": {
      "item": "hammersandtools:mining_core"
    },
    "L": {
      "item": "minecraft:lapis_block"
    },
    "C": {
      "item": "minecraft:copper_block"
    }
  },
  "result": {
    "item": "hammersandtools:reinforced_core",
    "count": 1
  }
}
"@
Save-Recipe "reinforced_core" $reinforcedCore

# NV3: advanced_core
$advancedCore = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "DQD",
    "ERE",
    "QGQ"
  ],
  "key": {
    "D": {
      "item": "minecraft:diamond_block"
    },
    "Q": {
      "item": "minecraft:quartz_block"
    },
    "E": {
      "item": "minecraft:emerald_block"
    },
    "R": {
      "item": "hammersandtools:reinforced_core"
    },
    "G": {
      "item": "minecraft:gold_block"
    }
  },
  "result": {
    "item": "hammersandtools:advanced_core",
    "count": 1
  }
}
"@
Save-Recipe "advanced_core" $advancedCore

# NV4: superior_core
$superiorCore = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "NDN",
    "QAQ",
    "ENE"
  ],
  "key": {
    "N": {
      "item": "minecraft:netherite_block"
    },
    "D": {
      "item": "minecraft:diamond_block"
    },
    "Q": {
      "item": "minecraft:quartz_block"
    },
    "A": {
      "item": "hammersandtools:advanced_core"
    },
    "E": {
      "item": "minecraft:emerald_block"
    }
  },
  "result": {
    "item": "hammersandtools:superior_core",
    "count": 1
  }
}
"@
Save-Recipe "superior_core" $superiorCore

# ==============================================================================
# DEFINICIÓN DE MATERIALES Y BLOQUES PARA MARTILLOS
# ==============================================================================
$hammerMaterials = @{
    "stone" = @{ "block" = "minecraft:stone"; "stick" = "minecraft:stick" }
    "iron" = @{ "block" = "minecraft:iron_block"; "stick" = "minecraft:iron_ingot" }
    "gold" = @{ "block" = "minecraft:gold_block"; "stick" = "minecraft:gold_ingot" }
    "diamond" = @{ "block" = "minecraft:diamond_block"; "stick" = "minecraft:diamond" }
    "netherite" = @{ "block" = "minecraft:netherite_block"; "stick" = "minecraft:netherite_ingot" }
}

# ==============================================================================
# 2. RECETAS DE MARTILLOS NIVEL 1 (Área 3x3)
# Fabricados con 5 Bloques del mineral + mining_core + mango
# BBB
# BCB
#  S 
# ==============================================================================
foreach ($mat in $hammerMaterials.Keys) {
    $info = $hammerMaterials[$mat]
    $hName = "${mat}_hammer"
    $recipeT1 = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "BBB",
    "BCB",
    " S "
  ],
  "key": {
    "B": {
      "item": "$($info.block)"
    },
    "C": {
      "item": "hammersandtools:mining_core"
    },
    "S": {
      "item": "$($info.stick)"
    }
  },
  "result": {
    "item": "hammersandtools:$hName",
    "count": 1
  }
}
"@
    Save-Recipe $hName $recipeT1
}

# ==============================================================================
# 3. RECETAS DE MARTILLOS NIVEL 2 (Volumen 3x3x3)
# Fabricados desde cero con 5 Bloques del mineral + reinforced_core + mango
# BBB
# BCB
#  S 
# ==============================================================================
foreach ($mat in $hammerMaterials.Keys) {
    $info = $hammerMaterials[$mat]
    $hName = "${mat}_hammer_t2"
    $recipeT2 = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "BBB",
    "BCB",
    " S "
  ],
  "key": {
    "B": {
      "item": "$($info.block)"
    },
    "C": {
      "item": "hammersandtools:reinforced_core"
    },
    "S": {
      "item": "$($info.stick)"
    }
  },
  "result": {
    "item": "hammersandtools:$hName",
    "count": 1
  }
}
"@
    Save-Recipe $hName $recipeT2
}

# ==============================================================================
# 4. RECETAS DE MARTILLOS NIVEL 3 (Área 5x5)
# Fabricados desde cero con 5 Bloques del mineral + advanced_core + mango
# BBB
# BCB
#  S 
# ==============================================================================
foreach ($mat in $hammerMaterials.Keys) {
    $info = $hammerMaterials[$mat]
    $hName = "${mat}_hammer_t3"
    $recipeT3 = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "BBB",
    "BCB",
    " S "
  ],
  "key": {
    "B": {
      "item": "$($info.block)"
    },
    "C": {
      "item": "hammersandtools:advanced_core"
    },
    "S": {
      "item": "$($info.stick)"
    }
  },
  "result": {
    "item": "hammersandtools:$hName",
    "count": 1
  }
}
"@
    Save-Recipe $hName $recipeT3
}

# ==============================================================================
# 5. RECETAS DE MARTILLOS NIVEL 4 (Volumen 5x5x5)
# Fabricados desde cero con 5 Bloques del mineral + superior_core + mango
# BBB
# BCB
#  S 
# ==============================================================================
foreach ($mat in $hammerMaterials.Keys) {
    $info = $hammerMaterials[$mat]
    $hName = "${mat}_hammer_t4"
    $recipeT4 = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "BBB",
    "BCB",
    " S "
  ],
  "key": {
    "B": {
      "item": "$($info.block)"
    },
    "C": {
      "item": "hammersandtools:superior_core"
    },
    "S": {
      "item": "$($info.stick)"
    }
  },
  "result": {
    "item": "hammersandtools:$hName",
    "count": 1
  }
}
"@
    Save-Recipe $hName $recipeT4
}

Write-Host "Todas las recetas JSON fueron generadas exitosamente: crafteo directo independiente con su respectivo núcleo y bloques de mineral."
