<script setup>
import axiosInstance from "../axiosConfig";
import {onMounted, ref} from "vue";
import Cookies from "js-cookie";
import { FwbDropdown, FwbListGroup, FwbListGroupItem } from 'flowbite-vue';

const peopleCount = ref('');

const fetchEntriesCount = async () => {
  try {
    const response = await axiosInstance.get('/person/count');
    peopleCount.value = response.data;
  } catch (error) {
    console.log("Stored Token: ", Cookies.get('jwtToken'));
    console.error("There was an error fetching the entries count:", error);
  }
};

onMounted(fetchEntriesCount);
</script>

<template>
<div id="banner">
  <p>Welcome Admin!</p>
  <fwb-dropdown text="Menu">
    <fwb-list-group>
      <fwb-list-group-item>Settings</fwb-list-group-item>
      <fwb-list-group-item>Statistics</fwb-list-group-item>
      <fwb-list-group-item>Logout</fwb-list-group-item>

    </fwb-list-group>

  </fwb-dropdown>
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
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text-box{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
}

.text-box p{
  padding: 2em 0;
}
</style>