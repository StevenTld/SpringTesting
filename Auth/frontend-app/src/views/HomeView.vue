<template>
  <div class="home-container">
    <h1>Bienvenue dans votre Gestionnaire</h1>

    <div class="quick-access">
      <router-link to="/dogs" class="card">
        <h2>Gestion des Chiens</h2>
        <p>Consultez et gérez la liste de vos chiens</p>
      </router-link>

      <router-link to="/students" class="card">
        <h2>Gestion des Étudiants</h2>
        <p>Consultez et gérez les informations des étudiants</p>
      </router-link>

      <router-link to="/academic" class="card">
        <h2>Années Académiques</h2>
        <p>Consultez et gérez les formations</p>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
}
</script>

<style scoped>
.home-container {
  text-align: center;
}

.home-container h1 {
  color: #333;
  margin-bottom: 30px;
}

.quick-access {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.card {
  background-color: #f4f4f4;
  border-radius: 8px;
  padding: 20px;
  width: 250px;
  text-decoration: none;
  color: #333;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.card:hover {
  transform: scale(1.05);
  background-color: #e7e7e7;
}

.card h2 {
  margin-bottom: 10px;
  color: #2c3e50;
}

.card p {
  color: #666;
}
</style>