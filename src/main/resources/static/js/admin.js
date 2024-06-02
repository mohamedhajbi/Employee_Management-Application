/* script.js */

const themes = [
    {
        background: "#1A1A2E",
        color: "#FFFFFF",
        primaryColor: "#0F3460",
    },
    {
        background: "#EFEFEF",
        color: "#000000",
        primaryColor: "#00A19D",
    },
];

let currentThemeIndex = 0;

function changeTheme() {
    currentThemeIndex = (currentThemeIndex + 1) % themes.length;
    const theme = themes[currentThemeIndex];
    document.documentElement.style.setProperty("--background", theme.background);
    document.documentElement.style.setProperty("--color", theme.color);
    document.documentElement.style.setProperty("--primary-color", theme.primaryColor);
}

document.addEventListener("DOMContentLoaded", () => {
    const themeBtnContainer = document.querySelector(".theme-btn-container");
    const themeBtn = document.createElement("img");
    themeBtn.src = "https://img.icons8.com/ios/50/000000/sun--v3.png";
    themeBtn.classList.add("theme-btn");
    themeBtnContainer.appendChild(themeBtn);

    themeBtn.addEventListener("click", changeTheme); 
});
