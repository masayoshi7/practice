<?php
require_once 'Zend/Controller/Action.php';
require_once dirname(__FILE__) . '/../models/regionDbModel.php';
require_once dirname(__FILE__) . '/../function/function.php';

Class TopController extends Zend_Controller_Action
{
    public function topAction()
    {
        $dbModel = new regionDbModel();
        $stmt    = $dbModel->allSerche();
        $this->view->assign('stmt', $stmt);
    }

    public function selectAction()
    {
        $conditions      = $this->_request->getPost('condition');
        $searchCondition = $this->_request->getPost('selectCondition');

        if ($conditions !== null) {
            $dbModel         = new regionDbModel();
            $conditions      = arrayconversion($conditions);
            $stmt            = $dbModel->serche($searchCondition, $conditions);
            $this->view->assign('stmt', $stmt);
            $this->view->assign('selectCountmsg', '検索結果は' . count($stmt) . '件です。');
        } else {
            $this->view->assign('errormsg', '検索対象を選択してください。');
        }

        $this->render('top');
    }

    public function insertAction()
    {
        $region   = $this->_request->getPost('region');
        $number   = $this->_request->getPost('number');
        $goods    = $this->_request->getPost('goods');
        $errormsg = validate($region, $number, $goods);

        // バリデーションを行い問題が無ければDBに挿入処理を行う
        if ($errormsg === '') {
            $insertInfo = [
                'id'         => null,
                'region'     => $region,
                'number'     => $number,
                'goods'      => $goods,
                'created_at' => date('Y-m-d H:i:s')
            ];
            $dbModel = new regionDbModel();
            $dbModel->insert($insertInfo);
        } else {
            $this->view->assign('errormsg', $errormsg);
        }
        $this->_forward('top');
    }

    public function deleteAction()
    {
        $id      = $this->_request->getPost('id');
        $dbModel = new regionDbModel();
        $dbModel->delete($id);
        $this->_forward('top');
    }

    public function updateAction()
    {
        $id           = $this->_request->getPost('id');
        $updateRegion = $this->_request->getPost('region');
        $updateNumber = $this->_request->getPost('number');
        $updateGoods  = $this->_request->getPost('goods');
        $errormsg     = validate($updateRegion, $updateNumber, $updateGoods);

        // バリデーションを行い問題が無ければDBに更新処理を行う
        if ($errormsg === '') {
            $dbModel = new regionDbModel();
            $dbModel->update($id, $updateRegion, $updateNumber, $updateGoods);
        } else {
            $this->view->assign('errormsg', $errormsg);
        }
        $this->_forward('top');
    }
}
