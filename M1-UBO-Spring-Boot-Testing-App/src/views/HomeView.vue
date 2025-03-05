<template>
  <div class="home">
    <div class="hero">
      <h1>UBO Student Management System</h1>
      <p>Manage students, formations, groups, and teaching units efficiently</p>
    </div>

    <div class="quick-actions">
      <h2>Quick Actions</h2>
      <div class="action-cards">
        <div class="action-card">
          <h3>Student Management</h3>
          <p>View, add, update, and delete student records</p>
          <router-link to="/students" class="action-button">Manage Students</router-link>
        </div>
        
        <div class="action-card">
          <h3>Academic Year Management</h3>
          <p>Manage academic years, formations, and their properties</p>
          <router-link to="/academic-years" class="action-button">Manage Academic Years</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
}
</script>

<style scoped>
.home {
  padding: 20px 0;
}

.hero {
  text-align: center;
  padding: 60px 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
  margin-bottom: 40px;
}

.hero h1 {
  margin-bottom: 20px;
  color: #2c3e50;
}

.quick-actions {
  margin-top: 40px;
}

.quick-actions h2 {
  margin-bottom: 20px;
  text-align: center;
  color: #2c3e50;
}

.action-cards {
  display: flex;
  justify-content: center;
  gap: 30px;
  flex-wrap: wrap;
}

.action-card {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 30px;
  width: 300px;
  text-align: center;
  transition: transform 0.3s ease;
}

.action-card:hover {
  transform: translateY(-5px);
}

.action-card h3 {
  margin-bottom: 15px;
  color: #2c3e50;
}

.action-card p {
  margin-bottom: 20px;
  color: #6c757d;
}

.action-button {
  display: inline-block;
  background-color: #3498db;
  color: white;
  padding: 10px 20px;
  text-decoration: none;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.action-button:hover {
  background-color: #2980b9;
}
</style>
