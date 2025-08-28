

class ColorMixer {
    constructor() {
        this.selectedColors = [];
        this.maxSelections = 2;
        this.init();
    }

    init() {
        this.bindEvents();
        this.loadHistory();
    }

    bindEvents() {
        // Värvinuppude sündmused
        document.querySelectorAll('.color-btn').forEach(btn => {
            btn.addEventListener('click', (e) => this.handleColorSelection(e));
        });

        // Segatud värvid nupu sündmus
        document.getElementById('mix-colors-btn').addEventListener('click', () => {
            this.mixColors();
        });
    }

    handleColorSelection(event) {
        const button = event.target;
        const colorId = parseInt(button.dataset.colorId);
        const colorName = button.dataset.colorName;
        const backgroundColor = button.style.backgroundColor;

        // Kontrolli, kas värv on juba valitud
        const isSelected = this.selectedColors.find(color => color.id === colorId);
        
        if (isSelected) {
            // Eemalda valik
            this.removeColorSelection(colorId);
            button.classList.remove('selected');
        } else {
            // Lisa valik, kui pole veel täis
            if (this.selectedColors.length < this.maxSelections) {
                this.addColorSelection(colorId, colorName, backgroundColor);
                button.classList.add('selected');
            } else {
                // Asenda esimene valik
                const firstButton = document.querySelector(`[data-color-id="${this.selectedColors[0].id}"]`);
                firstButton.classList.remove('selected');
                this.selectedColors.shift();
                this.addColorSelection(colorId, colorName, backgroundColor);
                button.classList.add('selected');
            }
        }

        // Uuenda kuvamist
        this.updateDisplay();
        
        // Uuenda segatud värvid nupu olekut
        this.updateMixButtonState();
    }

    addColorSelection(colorId, colorName, backgroundColor) {
        this.selectedColors.push({
            id: colorId,
            name: colorName,
            backgroundColor: backgroundColor
        });
    }

    removeColorSelection(colorId) {
        this.selectedColors = this.selectedColors.filter(color => color.id !== colorId);
    }

    updateDisplay() {
        const color1Display = document.getElementById('color1-display');
        const color2Display = document.getElementById('color2-display');

        // Tühjenda kõik
        color1Display.innerHTML = '<span>Vali esimene värv</span>';
        color1Display.style.backgroundColor = '#f0f0f0';
        color1Display.classList.remove('filled');

        color2Display.innerHTML = '<span>Vali teine värv</span>';
        color2Display.style.backgroundColor = '#f0f0f0';
        color2Display.classList.remove('filled');

        // Lisa valitud värvid
        if (this.selectedColors.length >= 1) {
            color1Display.innerHTML = `<span>${this.selectedColors[0].name}</span>`;
            color1Display.style.backgroundColor = this.selectedColors[0].backgroundColor;
            color1Display.classList.add('filled');
        }

        if (this.selectedColors.length >= 2) {
            color2Display.innerHTML = `<span>${this.selectedColors[1].name}</span>`;
            color2Display.style.backgroundColor = this.selectedColors[1].backgroundColor;
            color2Display.classList.add('filled');
        }
    }

    updateMixButtonState() {
        const mixButton = document.getElementById('mix-colors-btn');
        
        if (this.selectedColors.length === 2) {
            mixButton.disabled = false;
            mixButton.textContent = `Segatud värvid (${this.selectedColors[0].name} + ${this.selectedColors[1].name})`;
        } else {
            mixButton.disabled = true;
            mixButton.textContent = 'Segatud värvid';
        }
    }

    mixColors() {
        if (this.selectedColors.length !== 2) {
            alert('Palun vali kaks värvi segamiseks!');
            return;
        }

        const color1 = this.selectedColors[0];
        const color2 = this.selectedColors[1];

        // Arvuta keskmine värv
        const mixedColor = this.calculateMixedColor(color1.backgroundColor, color2.backgroundColor);
        
        // Salvesta andmebaasi
        this.saveMixedColor(color1.id, color2.id, mixedColor);
        
        // Kuva tulemus
        this.displayMixedColor(mixedColor, color1.name, color2.name);
        
        // Lisa visuaalne tagasiside
        this.showMixSuccess();
    }

    calculateMixedColor(color1Hex, color2Hex) {
        // Konverteeri hex RGB-ks
        const rgb1 = this.hexToRgb(color1Hex);
        const rgb2 = this.hexToRgb(color2Hex);

        // Arvuta keskmine
        const mixedR = Math.round((rgb1.r + rgb2.r) / 2);
        const mixedG = Math.round((rgb1.g + rgb2.g) / 2);
        const mixedB = Math.round((rgb1.b + rgb2.b) / 2);

        return {
            r: mixedR,
            g: mixedG,
            b: mixedB,
            hex: this.rgbToHex(mixedR, mixedG, mixedB),
            rgb: `rgb(${mixedR}, ${mixedG}, ${mixedB})`
        };
    }

    hexToRgb(hex) {
        // Eemalda # kui on olemas
        hex = hex.replace('#', '');
        
        // Konverteeri hex RGB-ks
        const r = parseInt(hex.substr(0, 2), 16);
        const g = parseInt(hex.substr(2, 2), 16);
        const b = parseInt(hex.substr(4, 2), 16);
        
        return { r, g, b };
    }

    rgbToHex(r, g, b) {
        return '#' + [r, g, b].map(x => {
            const hex = x.toString(16);
            return hex.length === 1 ? '0' + hex : hex;
        }).join('');
    }

    displayMixedColor(mixedColor, color1Name, color2Name) {
        const resultBox = document.getElementById('mixed-color-display');
        const colorName = document.getElementById('color-name');
        const colorHex = document.getElementById('color-hex');
        const colorRgb = document.getElementById('color-rgb');

        resultBox.style.backgroundColor = mixedColor.rgb;
        resultBox.innerHTML = `<span>${color1Name} + ${color2Name}</span>`;
        resultBox.classList.add('filled');

        colorName.textContent = `Nimi: ${color1Name} + ${color2Name}`;
        colorHex.textContent = `HEX: ${mixedColor.hex}`;
        colorRgb.textContent = `RGB: (${mixedColor.r}, ${mixedColor.g}, ${mixedColor.b})`;
    }

    showMixSuccess() {
        const mixButton = document.getElementById('mix-colors-btn');
        const originalText = mixButton.textContent;
        
        // Lisa visuaalne tagasiside
        mixButton.textContent = '✓ Salvestatud!';
        mixButton.style.background = 'linear-gradient(135deg, #28a745 0%, #20c997 100%)';
        
        // Taasta originaalne tekst 2 sekundi pärast
        setTimeout(() => {
            mixButton.textContent = originalText;
            mixButton.style.background = 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)';
        }, 2000);
    }

    clearMixedColor() {
        const resultBox = document.getElementById('mixed-color-display');
        const colorName = document.getElementById('color-name');
        const colorHex = document.getElementById('color-hex');
        const colorRgb = document.getElementById('color-rgb');

        resultBox.style.backgroundColor = '#f0f0f0';
        resultBox.innerHTML = '<span>Vali kaks värvi ja vajuta "Segatud värvid" nuppu</span>';
        resultBox.classList.remove('filled');

        colorName.textContent = '';
        colorHex.textContent = '';
        colorRgb.textContent = '';
    }

    async saveMixedColor(color1Id, color2Id, mixedColor) {
        try {
            const response = await fetch('/api/mixed-colors', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    color1Id: color1Id,
                    color2Id: color2Id,
                    name: null // Kasutame automaatset nime
                })
            });

            if (response.ok) {
                const savedMixedColor = await response.json();
                console.log('Segatud värv salvestatud:', savedMixedColor);
                this.loadHistory(); // Uuenda ajalugu
            } else {
                console.error('Viga segatud värvi salvestamisel:', response.statusText);
                alert('Viga segatud värvi salvestamisel!');
            }
        } catch (error) {
            console.error('Viga API kõnes:', error);
            alert('Viga ühendamisel serveriga!');
        }
    }

    async loadHistory() {
        try {
            const response = await fetch('/api/mixed-colors');
            if (response.ok) {
                const mixedColors = await response.json();
                this.displayHistory(mixedColors);
            } else {
                console.error('Viga ajaloo laadimisel:', response.statusText);
            }
        } catch (error) {
            console.error('Viga API kõnes:', error);
        }
    }

    displayHistory(mixedColors) {
        const historyList = document.getElementById('history-list');
        historyList.innerHTML = '';

        if (mixedColors.length === 0) {
            historyList.innerHTML = '<p style="text-align: center; color: #666; grid-column: 1 / -1;">Pole veel segatud värve</p>';
            return;
        }

        mixedColors.forEach(mixedColor => {
            const historyItem = document.createElement('div');
            historyItem.className = 'history-item';
            
            historyItem.innerHTML = `
                <div class="history-colors">
                    <div class="history-color" style="background-color: ${mixedColor.color1.hex}"></div>
                    <div class="history-mix-symbol">+</div>
                    <div class="history-color" style="background-color: ${mixedColor.color2.hex}"></div>
                </div>
                <div class="history-result" style="background-color: ${mixedColor.hex}"></div>
                <div class="history-info">
                    <h4>${mixedColor.name}</h4>
                    <p>HEX: ${mixedColor.hex}</p>
                    <p>RGB: (${mixedColor.resultR}, ${mixedColor.resultG}, ${mixedColor.resultB})</p>
                </div>
            `;
            
            historyList.appendChild(historyItem);
        });
    }
}

// Käivita rakendus kui leht on laetud
document.addEventListener('DOMContentLoaded', () => {
    new ColorMixer();
});
