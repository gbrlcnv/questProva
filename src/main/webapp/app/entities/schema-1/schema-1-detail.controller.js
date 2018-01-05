(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .controller('Schema1DetailController', Schema1DetailController);

    Schema1DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Schema1', 'SchemaField', 'SchemaAnswer'];

    function Schema1DetailController($scope, $rootScope, $stateParams, previousState, entity, Schema1, SchemaField, SchemaAnswer) {
        var vm = this;

        vm.schema1 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('questProvaApp:schema1Update', function(event, result) {
            vm.schema1 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
