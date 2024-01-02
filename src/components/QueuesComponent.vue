<script setup>
import BannerComponent from "@/components/BannerComponent.vue";
import {onMounted, ref} from "vue";
import axiosInstance from "@/axiosConfig";

const queues = ref('');
// const newQueue = ref('');
// const name = ref('');
// const status = ref('');
// const details = {name: name.value, status: status.value}

const fetchAllQueues = async() => {
  try{
    const response = await axiosInstance.get('queue');
    queues.value = response.data;
  }catch (error){
    console.log("Error fetching all queues:", error);
  }
}

// const addNewQueue = async(details) => {
//   try{
//     const response = await axiosInstance.post('queue');
//     console.log(response.data);
//   } catch (error) {
//     console.log("Error adding new queue:", error);
//   }
// }
onMounted(fetchAllQueues);
</script>
<template>
  <BannerComponent />
    <p>The following are the queues</p>
      <ul>
        <li v-for="queue in queues" :key="queue">
         {{queue.queueName}}</li>
      </ul>

<!--    <div id="add-new-task">-->
<!--      <textarea v-model="newQueue" placeholder="Add new task"></textarea>-->
<!--      <button id="add-button" @click="addNewTask">Add</button>-->
<!--    </div>-->

</template>
<style scoped></style>