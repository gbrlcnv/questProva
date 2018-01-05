(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .controller('SchemaAnswerFieldDialogController', SchemaAnswerFieldDialogController);

    SchemaAnswerFieldDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'SchemaAnswerField', 'SchemaAnswer', 'SchemaField'];

    function SchemaAnswerFieldDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, SchemaAnswerField, SchemaAnswer, SchemaField) {
        var vm = this;

        vm.schemaAnswerField = entity;
        vm.clear = clear;
        vm.save = save;
        vm.schemaanswers = SchemaAnswer.query();
        vm.fields = SchemaField.query({filter: 'schemaanswerfield-is-null'});
        $q.all([vm.schemaAnswerField.$promise, vm.fields.$promise]).then(function() {
            if (!vm.schemaAnswerField.field || !vm.schemaAnswerField.field.id) {
                return $q.reject();
            }
            return SchemaField.get({id : vm.schemaAnswerField.field.id}).$promise;
        }).then(function(field) {
            vm.fields.push(field);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.schemaAnswerField.id !== null) {
                SchemaAnswerField.update(vm.schemaAnswerField, onSaveSuccess, onSaveError);
            } else {
                SchemaAnswerField.save(vm.schemaAnswerField, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('questProvaApp:schemaAnswerFieldUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
