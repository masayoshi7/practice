<?php
require_once 'Zend/Db.php';

Class regionDbModel
{
    /*
    * 全てのカラムを取得する関数
    * @param  void
    * @return {mixArray} {regionDBの削除フラグが立っていないカラムの配列}
    */
    public function allSerche()
    {
        $db  = Zend_Registry::get('dbInstance');
        $sql = 'SELECT `id`, `region`, `number`, `goods`, `created_at`, `updated_at` FROM `region` WHERE `is_deleted` = 0';
        try {
            $result = $db->fetchAll($sql);
        } catch (Zend_Exception $e) {
            echo $e->getCode();
            echo $e->getMessage();
        }
        return $result;
    }

    /*
    * 検索条件からカラムを取得する関数
    * @param  {String, StringArray} {$searchCondition, $conditions} {検索するキーワードと検索場所の配列}
    * @return {mixArray} {regionDBの削除フラグが立っていない検索条件に合致したカラムの配列}
    */
    public function serche($searchCondition, $conditions)
    {
        $db     = Zend_Registry::get('dbInstance');
        $select = $db->select();

        try {
            $column = [
                'id',
                'region',
                'number',
                'goods',
                'created_at',
                'updated_at',
                'is_deleted'
            ];
            $select->from('region', $column);

            foreach ($conditions as $value) {
                $select->orwhere($value . ' = ?', $searchCondition);
            }

            $select->where('is_deleted = ?', 0);
            $rows = $db->fetchAll($select);

            return $rows;
        } catch (Zend_Exception $e) {
            echo $e->getCode();
            echo $e->getMessage();
        }

    }

    /*
    * DBにインサートする関数
    * @param  {mixArray} {$insertInfo} {挿入するデータの入った配列}
    * @return void
    */
    public function insert($insertInfo)
    {
        $db = Zend_Registry::get('dbInstance');

        try {
            $db->insert('region', $insertInfo);
        } catch (Zend_Exception $e) {
            echo $e->getCode();
            echo $e->getMessage();
        }
    }

    /*
    * DBのカラムに削除フラグを立てる関数
    * @param  {int} {$id} {削除フラグを立てるカラムのid}
    * @return void
    */
    public function delete($id)
    {
        $db              = Zend_Registry::get('dbInstance');
        $updateContent   = [
            'is_deleted' => $db->quote(1, Zend_Db::INT_TYPE)
        ];
        $where           = 'id = ' . $db->quote($id, Zend_Db::INT_TYPE);
        try {
            $db->update('region', $updateContent, $where);
        } catch (Zend_Exception $e) {
            echo $e->getCode();
            echo $e->getMessage();
        }
    }

    /*
    * DBのカラムの情報を更新する関数
    * @param  {int,String,int,String} {$id, $updateRegion, $updateNumber, $updateGoods}
                                      {削除フラグを立てるカラムのidと更新する情報}
    * @return void
    */
    public function update($id, $updateRegion, $updateNumber, $updateGoods)
    {
        $db            = Zend_Registry::get('dbInstance');
        $updateContent = [
            'region'     => $updateRegion,
            'number'     => $db->quote($updateNumber, Zend_Db::INT_TYPE),
            'goods'      => $updateGoods,
            'updated_at' => date('Y-m-d H:i:s')
        ];
        $where         = 'id = ' . $db->quote($id, Zend_Db::INT_TYPE);
        try {
            $db->update('region', $updateContent, $where);
        } catch (Zend_Exception $e) {
            echo $e->getCode();
            echo $e->getMessage();
        }
    }
}
