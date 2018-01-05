'use strict';

describe('Controller Tests', function() {

    describe('SchemaAnswerField Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSchemaAnswerField, MockSchemaAnswer, MockSchemaField;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSchemaAnswerField = jasmine.createSpy('MockSchemaAnswerField');
            MockSchemaAnswer = jasmine.createSpy('MockSchemaAnswer');
            MockSchemaField = jasmine.createSpy('MockSchemaField');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SchemaAnswerField': MockSchemaAnswerField,
                'SchemaAnswer': MockSchemaAnswer,
                'SchemaField': MockSchemaField
            };
            createController = function() {
                $injector.get('$controller')("SchemaAnswerFieldDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'questProvaApp:schemaAnswerFieldUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
