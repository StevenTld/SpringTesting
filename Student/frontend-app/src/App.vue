<template>
<div id="app">
<nav>
  <div class="nav-container">
    <router-link to="/" class="brand">Mon Application</router-link>
    <div class="nav-links">
      <router-link to="/">Accueil</router-link>
      <router-link to="/dogs">Chiens</router-link>
      <router-link to="/students">Students</router-link>
      <router-link to="/academic">Academic Year</router-link>
    </div>
  </div>
</nav>

<router-view class="main-content"></router-view>
</div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  background-color: #f4f4f4;
}

#app {
  max-width: 1200px;
  margin: 0 auto;
}

nav {
  background-color: #333;
  color: white;
  padding: 15px 0;
}

.nav-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1100px;
  margin: 0 auto;
  padding: 0 15px;
}

.brand {
  font-size: 1.5em;
  font-weight: bold;
  color: white;
  text-decoration: none;
}

.nav-links {
  display: flex;
  gap: 15px;
}

.nav-links a {
  color: white;
  text-decoration: none;
  transition: color 0.3s ease;
}

.nav-links a:hover {
  color: #ccc;
}

.main-content {
  padding: 20px;
  background-color: white;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}
</style>