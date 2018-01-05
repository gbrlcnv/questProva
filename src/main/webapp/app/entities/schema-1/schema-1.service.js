(function() {
    'use strict';
    angular
        .module('questProvaApp')
        .factory('Schema1', Schema1);

    Schema1.$inject = ['$resource', 'DateUtils'];

    function Schema1 ($resource, DateUtils) {
        var resourceUrl =  'api/schema-1-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.versionDate = DateUtils.convertDateTimeFromServer(data.versionDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
