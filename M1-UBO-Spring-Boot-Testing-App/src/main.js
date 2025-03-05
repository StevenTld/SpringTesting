import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './assets/styles.css'

// Create and mount the Vue application
createApp(App)
  .use(router)
  .mount('#app')
