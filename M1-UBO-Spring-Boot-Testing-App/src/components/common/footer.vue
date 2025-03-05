<template>
  <footer class="footer">
    <div class="footer-content">
      <p>&copy; {{ currentYear }} UBO Student Management - University of Western Brittany</p>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'Footer',
  computed: {
    currentYear() {
      return new Date().getFullYear();
    }
  }
}
</script>

<style scoped>
.footer {
  background-color: #f8f9fa;
  padding: 1rem 0;
  margin-top: auto;
  border-top: 1px solid #e9ecef;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  text-align: center;
  color: #6c757d;
}
</style>
