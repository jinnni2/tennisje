<template>

    <v-card outlined>
        <v-card-title>
            ReservationList # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="UserId" v-model="item.userId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="ReserveId" v-model="item.reserveId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="CountName" v-model="item.countName" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Status" v-model="item.status" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'ReservationListViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/reservationLists/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

