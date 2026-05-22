/**
 * Tailwind CSS 配置文件
 * 功能：原子化 CSS 框架配置，自定义主题
 */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      // 自定义颜色，与后端欢迎页呼应的渐变色
      colors: {
        primary: '#667eea',
        secondary: '#764ba2',
      }
    },
  },
  plugins: [],
}
