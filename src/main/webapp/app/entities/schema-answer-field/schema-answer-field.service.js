(function() {
    'use strict';
    angular
        .module('questProvaApp')
        .factory('SchemaAnswerField', SchemaAnswerField);

    SchemaAnswerField.$inject = ['$resource'];

    function SchemaAnswerField ($resource) {
        var resourceUrl =  'api/schema-answer-fields/:id';

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
