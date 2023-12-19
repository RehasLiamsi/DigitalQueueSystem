<script setup>
import axiosInstance from "../axiosConfig";
import {onMounted, ref} from "vue";
import Cookies from "js-cookie";
import BannerComponent from "@/components/BannerComponent.vue";

const peopleCount = ref('');
const activeQueue = ref('');

const fetchEntriesCount = async () => {
  try {
    const response = await axiosInstance.get('/person/count/activeQueue');
    peopleCount.value = response.data;
  } catch (error) {
    console.log("Stored Token: ", Cookies.get('jwtToken'));
    console.error("There was an error fetching the entries count:", error);
  }
};

const fetchActiveQueueName = async () => {
  try {
    const response = await axiosInstance.get('/queue/active/name');
    activeQueue.value = response.data;
  } catch (error) {
    console.log("Stored Token: ", Cookies.get('jwtToken'));
    console.error("There was an error fetching the active queue:", error);
  }
}


onMounted(fetchEntriesCount);
onMounted(fetchActiveQueueName);
</script>

<template>
  <BannerComponent />

  <div class="text-box">
    <p v-if="activeQueue===''"> No queue is active.</p>
    <p v-else>The active queue is "<span> {{ activeQueue}}</span>"
    <br><br>There are <span> {{ peopleCount }} </span> people in the queue</p>
  </div>
</template>

<style scoped>


.text-box{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
  flex-direction: column;
  margin: 2em 0;
}


</style>