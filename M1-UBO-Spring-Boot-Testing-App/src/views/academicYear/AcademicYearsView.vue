// views/academicYear/AcademicYearsView.vue
<template>
  <div class="academic-years-view">
    <h1>Academic Years Management</h1>
    <AcademicYearList @academic-year-deleted="handleAcademicYearDeleted" />
  </div>
</template>

<script>
import AcademicYearList from '@/components/academicYear/AcademicYearList.vue';

export default {
  name: 'AcademicYearsView',
  components: {
    AcademicYearList
  },
  methods: {
    handleAcademicYearDeleted(academicYearId) {
      this.$router.push('/academic-years');
    }
  }
};
</script>

// views/academicYear/AcademicYearCreateView.vue
<template>
  <div class="academic-year-create-view">
    <h1>Create New Academic Year</h1>
    <AcademicYearForm 
      :submit-label="'Create Academic Year'" 
      @submit="createAcademicYear" 
      @cancel="cancel" 
    />
  </div>
</template>

<script>
import AcademicYearForm from '@/components/academicYear/AcademicYearForm.vue';
import AcademicYearService from '@/services/AcademicYearService.js';

export default {
  name: 'AcademicYearCreateView',
  components: {
    AcademicYearForm
  },
  data() {
    return {
      error: null
    };
  },
  methods: {
    async createAcademicYear(academicYearData) {
      try {
        const createdAcademicYear = await AcademicYearService.createAcademicYear(academicYearData);
        this.$router.push(`/academic-years/${createdAcademicYear.id}`);
      } catch (error) {
        this.error = 'Failed to create academic year. Please try again.';
        console.error(error);
      }
    },
    cancel() {
      this.$router.push('/academic-years');
    }
  }
};
</script>

// views/academicYear/AcademicYearEditView.vue
<template>
  <div class="academic-year-edit-view">
    <h1>Edit Academic Year</h1>
    <div v-if="loading" class="loading">
      Loading academic year data...
    </div>
    <div v-else-if="error" class="error">
      {{ error }}
    </div>
    <div v-else-if="!academicYear" class="not-found">
      Academic year not found.
    </div>
    <AcademicYearForm 
      v-else
      :initial-academic-year="academicYear" 
      :submit-label="'Update Academic Year'" 
      @submit="updateAcademicYear" 
      @cancel="cancel" 
    />
  </div>
</template>

<script>
import AcademicYearForm from '@/components/academicYear/AcademicYearForm.vue';
import AcademicYearService from '@/services/AcademicYearService.js';

export default {
  name: 'AcademicYearEditView',
  components: {
    AcademicYearForm
  },
  props: {
    id: {
      type: [Number, String],
      required: true
    }
  },
  data() {
    return {
      academicYear: null,
      loading: true,
      error: null
    };
  },
  created() {
    this.fetchAcademicYear();
  },
  methods: {
    async fetchAcademicYear() {
      this.loading = true;
      this.error = null;
      try {
        this.academicYear = await AcademicYearService.getAcademicYearById(this.id);
      } catch (error) {
        this.error = 'Failed to load academic year data. Please try again later.';
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
    async updateAcademicYear(academicYearData) {
      try {
        await AcademicYearService.updateAcademicYear(this.id, academicYearData);
        this.$router.push(`/academic-years/${this.id}`);
      } catch (error) {
        this.error = 'Failed to update academic year. Please try again.';
        console.error(error);
      }
    },
    cancel() {
      this.$router.push(`/academic-years/${this.id}`);
    }
  },
  watch: {
    id() {
      this.fetchAcademicYear();
    }
  }
};
</script>

<style scoped>
.loading, .error, .not-found {
  text-align: center;
  padding: 20px;
  margin: 20px 0;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.error {
  color: #dc3545;
}
</style>

// views/academicYear/AcademicYearDetailsView.vue
<template>
  <div class="academic-year-details-view">
    <h1>Academic Year Details</h1>
    <AcademicYearDetails :id="id" />
  </div>
</template>

<script>
import AcademicYearDetails from '@/components/academicYear/AcademicYearDetails.vue';

export default {
  name: 'AcademicYearDetailsView',
  components: {
    AcademicYearDetails
  },
  props: {
    id: {
      type: [Number, String],
      required: true
    }
  }
};
</script>
