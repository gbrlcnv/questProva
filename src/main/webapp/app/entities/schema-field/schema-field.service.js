(function() {
    'use strict';
    angular
        .module('questProvaApp')
        .factory('SchemaField', SchemaField);

    SchemaField.$inject = ['$resource'];

    function SchemaField ($resource) {
        var resourceUrl =  'api/schema-fields/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
