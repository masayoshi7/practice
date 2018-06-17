<?php
// フロントコントローラをrequire
require_once 'Zend/Controller/Front.php';
require_once 'Zend/Db.php';
require_once 'Zend/Registry.php';

$db = Zend_Db::factory('Pdo_Mysql', array(
    'host'=> 'localhost',
    'username' => 'nishiguchi',
    'password' => 'nishiguchi',
    'dbname'   => 'nishiguchi',
    'charset'  => 'utf8'
));
Zend_Registry::set('dbInstance', $db);

$zend = Zend_Controller_Front::run('../application/controllers');
