<script setup>
import axiosInstance from "@/axiosConfig";
import {onMounted, ref} from "vue";

const showDropdown = ref(false)
const activeQueueId = ref('');


const fetchActiveQueueId = async () => {
  try {
    const response = await axiosInstance.get('queue/active/id')
    activeQueueId.value = response.data;
  } catch (error) {
    console.error("There was an error fetching the active queue Id:", error);
  }
}

const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value
}

onMounted(fetchActiveQueueId);

</script>

<template>
  <div id="banner">
    <p>Welcome Admin!</p>
    <div class="router-links">
      <RouterLink to="/admin">Home</RouterLink>
      <div class="dropdown" @click="toggleDropdown">
        <span class="dropdown-link">Settings</span>
        <div v-if="showDropdown" class="dropdown-content">
          <RouterLink to="/queues">Queues</RouterLink>
        </div>
      </div>
      <RouterLink to="/logout">Logout</RouterLink>
    </div>
  </div>
</template>

<style scoped>
#banner {
  background: #0167b2;
  padding: 0.4em 4em;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
}

.router-links {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.dropdown-link,
.router-links a {
  text-decoration: none;
  color: white;
  cursor: pointer;
  margin: 10px;
}

.dropdown-link {
  display: inline-flex;
  margin-bottom: 0;
}

.dropdown-content {
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  position: absolute;
  background-color: #f9f9f9;
  color: black;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  padding: 0;
  margin-top: 0.5em;
  min-width: 100px;
  cursor: pointer;
}

.dropdown-content ul {
  list-style: none;
  margin: 0;
  padding: 0.5em 0 0.5em 1em;
}

.dropdown-content a {
  padding: 0.2em;
  width: auto;
  text-decoration: none;
  color: black;
}

p {
  margin-bottom: 0;
}
</style>