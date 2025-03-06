import { createApp } from 'vue'
import App from './App.vue'
import router from './router' // Import du fichier router

const app = createApp(App)
app.use(router) // Utilisation du router
app.mount('#app')