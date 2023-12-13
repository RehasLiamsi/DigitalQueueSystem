<script setup>
import axios from "axios";
import {onMounted, ref} from "vue";

const peopleCount = ref(0);

const fetchEntriesCount = async () => {
  try {
    const response = await axios.get('http://localhost:8080/person/count');
    peopleCount.value = response.data;
  } catch (error) {
    console.error("There was an error fetching the entries count:", error);
  }
};

onMounted(fetchEntriesCount);
</script>

<template>
<div id="banner">
  <p>Welcome Admin!</p>
</div>
  <div class="text-box">
    <p>There are <span> {{ peopleCount }} </span> people in the queue</p>
  </div>
</template>

<style scoped>
#banner{
  background: #0167b2;
  padding: 0.4em 2em;
  color: white;
}
</style>