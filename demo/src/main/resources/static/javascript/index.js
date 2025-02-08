const sections = document.querySelectorAll('.image-section');
let currentIndex = 0;

// 🔹 모든 섹션 숨기고 하나씩 보이게 하는 함수
const showSection = (index) => {
    sections.forEach((section, i) => {
        section.style.display = i === index ? "flex" : "none";
    });
};

// 초기화 (첫 번째 섹션 보이기)
showSection(currentIndex);

// 3초마다 섹션 변경
let sectionInterval = setInterval(() => {
    currentIndex = (currentIndex + 1) % sections.length;
    showSection(currentIndex);
}, 3000);

// 마우스를 올리면 슬라이드 멈추기
document.querySelector('.container').addEventListener('mouseenter', () => {
    clearInterval(sectionInterval);
});

// 마우스를 벗어나면 다시 자동 실행
document.querySelector('.container').addEventListener('mouseleave', () => {
    sectionInterval = setInterval(() => {
        currentIndex = (currentIndex + 1) % sections.length;
        showSection(currentIndex);
    }, 3000);
});
