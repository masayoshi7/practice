<?php
require_once 'Zend/Controller/Action.php';

Class ErrorController extends Zend_Controller_Action
{
    public function errorAction()
    {
        $errors = $this->_getParam('error_handler');

        switch ($errors->type) {
            case Zend_Controller_Plugin_ErrorHandler::EXCEPTION_NO_CONTROLLER:
            case Zend_Controller_Plugin_ErrorHandler::EXCEPTION_NO_ACTION:
                $this->view->errormsg = 'リクエストされたコントローラ・アクションは存在しません。';
                break;
            default:
                $this->view->errormsg = $errors->exception->getMessage();
                break;
        }
    }
}
