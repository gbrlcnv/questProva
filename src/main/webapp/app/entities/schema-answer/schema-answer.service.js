(function() {
    'use strict';
    angular
        .module('questProvaApp')
        .factory('SchemaAnswer', SchemaAnswer);

    SchemaAnswer.$inject = ['$resource', 'DateUtils'];

    function SchemaAnswer ($resource, DateUtils) {
        var resourceUrl =  'api/schema-answers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                        data.completionDate = DateUtils.convertDateTimeFromServer(data.completionDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
