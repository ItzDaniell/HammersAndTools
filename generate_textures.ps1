Add-Type -AssemblyName System.Drawing

$destDir = "D:\MinecraftModsDev\HammersAndTools\src\main\resources\assets\hammersandtools\textures\item"

function Create-CoreTexture($name, $outerColor, $innerColor, $accentColor) {
    $bmp = New-Object System.Drawing.Bitmap 16, 16
    for ($x = 0; $x -lt 16; $x++) {
        for ($y = 0; $y -lt 16; $y++) {
            $bmp.SetPixel($x, $y, [System.Drawing.Color]::FromArgb(0, 0, 0, 0))
        }
    }
    # Marco exterior de núcleo
    for ($x = 3; $x -le 12; $x++) {
        $bmp.SetPixel($x, 3, $outerColor)
        $bmp.SetPixel($x, 12, $outerColor)
    }
    for ($y = 3; $y -le 12; $y++) {
        $bmp.SetPixel(3, $y, $outerColor)
        $bmp.SetPixel(12, $y, $outerColor)
    }
    # Relleno interior
    for ($x = 4; $x -le 11; $x++) {
        for ($y = 4; $y -le 11; $y++) {
            $bmp.SetPixel($x, $y, $innerColor)
        }
    }
    # Gema / acento central
    for ($x = 6; $x -le 9; $x++) {
        for ($y = 6; $y -le 9; $y++) {
            $bmp.SetPixel($x, $y, $accentColor)
        }
    }
    $path = Join-Path $destDir "$name.png"
    $bmp.Save($path, [System.Drawing.Imaging.ImageFormat]::Png)
    $bmp.Dispose()
}

function Create-HammerTexture($name, $headColor, $headHighlight, $isT2) {
    $bmp = New-Object System.Drawing.Bitmap 16, 16
    for ($x = 0; $x -lt 16; $x++) {
        for ($y = 0; $y -lt 16; $y++) {
            $bmp.SetPixel($x, $y, [System.Drawing.Color]::FromArgb(0, 0, 0, 0))
        }
    }
    $stickColor = [System.Drawing.Color]::FromArgb(255, 139, 90, 43)

    # Mango diagonal
    $stickCoords = @(
        @(2, 13), @(3, 12), @(4, 11), @(5, 10), @(6, 9), @(7, 8)
    )
    foreach ($c in $stickCoords) {
        $bmp.SetPixel($c[0], $c[1], $stickColor)
    }

    # Cabeza ancha del martillo (esquina superior derecha)
    for ($x = 7; $x -le 13; $x++) {
        for ($y = 2; $y -le 6; $y++) {
            $bmp.SetPixel($x, $y, $headColor)
        }
    }
    # Brillo superior
    for ($x = 8; $x -le 12; $x++) {
        $bmp.SetPixel($x, 2, $headHighlight)
    }
    # Si es T2, agregamos refuerzos dorados/místicos en los laterales
    if ($isT2) {
        $goldPlate = [System.Drawing.Color]::FromArgb(255, 255, 215, 0)
        $bmp.SetPixel(6, 3, $goldPlate)
        $bmp.SetPixel(6, 4, $goldPlate)
        $bmp.SetPixel(6, 5, $goldPlate)
        $bmp.SetPixel(14, 3, $goldPlate)
        $bmp.SetPixel(14, 4, $goldPlate)
        $bmp.SetPixel(14, 5, $goldPlate)
    }

    $path = Join-Path $destDir "$name.png"
    $bmp.Save($path, [System.Drawing.Imaging.ImageFormat]::Png)
    $bmp.Dispose()
}

# 1. Núcleos
Create-CoreTexture "mining_core" ([System.Drawing.Color]::FromArgb(255, 180, 180, 180)) ([System.Drawing.Color]::FromArgb(255, 200, 30, 30)) ([System.Drawing.Color]::FromArgb(255, 80, 220, 240))
Create-CoreTexture "reinforced_core" ([System.Drawing.Color]::FromArgb(255, 80, 80, 90)) ([System.Drawing.Color]::FromArgb(255, 50, 80, 200)) ([System.Drawing.Color]::FromArgb(255, 255, 215, 0))
Create-CoreTexture "advanced_core" ([System.Drawing.Color]::FromArgb(255, 255, 215, 0)) ([System.Drawing.Color]::FromArgb(255, 20, 180, 180)) ([System.Drawing.Color]::FromArgb(255, 180, 50, 240))
Create-CoreTexture "superior_core" ([System.Drawing.Color]::FromArgb(255, 75, 65, 75)) ([System.Drawing.Color]::FromArgb(255, 220, 100, 255)) ([System.Drawing.Color]::FromArgb(255, 255, 255, 255))
Create-CoreTexture "tree_core" ([System.Drawing.Color]::FromArgb(255, 139, 90, 43)) ([System.Drawing.Color]::FromArgb(255, 34, 139, 34)) ([System.Drawing.Color]::FromArgb(255, 50, 205, 50))
Create-CoreTexture "excavation_core" ([System.Drawing.Color]::FromArgb(255, 160, 82, 45)) ([System.Drawing.Color]::FromArgb(255, 210, 180, 140)) ([System.Drawing.Color]::FromArgb(255, 245, 222, 179))

# 2. Martillos T1
Create-HammerTexture "stone_hammer" ([System.Drawing.Color]::FromArgb(255, 128, 128, 128)) ([System.Drawing.Color]::FromArgb(255, 180, 180, 180)) $false
Create-HammerTexture "iron_hammer" ([System.Drawing.Color]::FromArgb(255, 215, 215, 215)) ([System.Drawing.Color]::FromArgb(255, 250, 250, 250)) $false
Create-HammerTexture "gold_hammer" ([System.Drawing.Color]::FromArgb(255, 250, 215, 40)) ([System.Drawing.Color]::FromArgb(255, 255, 245, 150)) $false
Create-HammerTexture "diamond_hammer" ([System.Drawing.Color]::FromArgb(255, 45, 220, 210)) ([System.Drawing.Color]::FromArgb(255, 170, 250, 250)) $false
Create-HammerTexture "netherite_hammer" ([System.Drawing.Color]::FromArgb(255, 75, 65, 70)) ([System.Drawing.Color]::FromArgb(255, 120, 110, 115)) $false

# 3. Martillos T2
Create-HammerTexture "stone_hammer_t2" ([System.Drawing.Color]::FromArgb(255, 128, 128, 128)) ([System.Drawing.Color]::FromArgb(255, 180, 180, 180)) $true
Create-HammerTexture "iron_hammer_t2" ([System.Drawing.Color]::FromArgb(255, 215, 215, 215)) ([System.Drawing.Color]::FromArgb(255, 250, 250, 250)) $true
Create-HammerTexture "gold_hammer_t2" ([System.Drawing.Color]::FromArgb(255, 250, 215, 40)) ([System.Drawing.Color]::FromArgb(255, 255, 245, 150)) $true
Create-HammerTexture "diamond_hammer_t2" ([System.Drawing.Color]::FromArgb(255, 45, 220, 210)) ([System.Drawing.Color]::FromArgb(255, 170, 250, 250)) $true
Create-HammerTexture "netherite_hammer_t2" ([System.Drawing.Color]::FromArgb(255, 75, 65, 70)) ([System.Drawing.Color]::FromArgb(255, 120, 110, 115)) $true

Write-Host "Todas las texturas PNG de núcleos y martillos fueron generadas exitosamente."
