<template>

    <v-data-table
        :headers="headers"
        :items="selectCategory"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'SelectCategoryView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            selectCategory : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/selectCategories'))

            temp.data._embedded.selectCategories.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.selectCategory = temp.data._embedded.selectCategories;
        },
        methods: {
        }
    }
</script>

