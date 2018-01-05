(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('schema-answer-field', {
            parent: 'entity',
            url: '/schema-answer-field',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'questProvaApp.schemaAnswerField.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/schema-answer-field/schema-answer-fields.html',
                    controller: 'SchemaAnswerFieldController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schemaAnswerField');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('schema-answer-field-detail', {
            parent: 'schema-answer-field',
            url: '/schema-answer-field/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'questProvaApp.schemaAnswerField.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/schema-answer-field/schema-answer-field-detail.html',
                    controller: 'SchemaAnswerFieldDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schemaAnswerField');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SchemaAnswerField', function($stateParams, SchemaAnswerField) {
                    return SchemaAnswerField.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'schema-answer-field',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('schema-answer-field-detail.edit', {
            parent: 'schema-answer-field-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-answer-field/schema-answer-field-dialog.html',
                    controller: 'SchemaAnswerFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchemaAnswerField', function(SchemaAnswerField) {
                            return SchemaAnswerField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('schema-answer-field.new', {
            parent: 'schema-answer-field',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-answer-field/schema-answer-field-dialog.html',
                    controller: 'SchemaAnswerFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('schema-answer-field', null, { reload: 'schema-answer-field' });
                }, function() {
                    $state.go('schema-answer-field');
                });
            }]
        })
        .state('schema-answer-field.edit', {
            parent: 'schema-answer-field',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-answer-field/schema-answer-field-dialog.html',
                    controller: 'SchemaAnswerFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchemaAnswerField', function(SchemaAnswerField) {
                            return SchemaAnswerField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('schema-answer-field', null, { reload: 'schema-answer-field' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('schema-answer-field.delete', {
            parent: 'schema-answer-field',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-answer-field/schema-answer-field-delete-dialog.html',
                    controller: 'SchemaAnswerFieldDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SchemaAnswerField', function(SchemaAnswerField) {
                            return SchemaAnswerField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('schema-answer-field', null, { reload: 'schema-answer-field' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
