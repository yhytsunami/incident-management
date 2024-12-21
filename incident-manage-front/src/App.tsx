import { useState,useEffect } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Table, Divider,message, Pagination } from "antd";
import dayjs from "dayjs";
import Incident from './views/Incident';
import AddIncident from './components/AddIncident'
import UpdIncident from './components/UpdIncident'
import { queryIncidents, deleteIncident } from "./api/Incidents";

function App() {
  const [count, setCount] = useState(0)
  const [visiable, setVisiable] = useState(false);
  const [dataSource, setDataSource] = useState();
  const [showEdit, setShowEdit] = useState(false);
  const [mkItem, setMkItem] = useState(null);

  const columns = [
    {
      title: "Incident Name",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Incident Description",
      dataIndex: "description",
      key: "description",
      style: { minWidth: "200px" },
    },
    {
      title: "Create Time",
      dataIndex: "createTime",
      key: "createTime",
      render: (text, record) => (
        <span>{dayjs(record.createTime).format("YYYY-MM-DD HH:mm:ss")}</span>
      ),
      style: { minWidth: "200px" },
    },
    {
      title: "Create Time",
      dataIndex: "updateTime",
      key: "updateTime",
      render: (text, record) => (
        <span>{dayjs(record.updateTime).format("YYYY-MM-DD HH:mm:ss")}</span>
      ),
      style: { minWidth: "200px" },
    },
    {
      title: "Status",
      dataIndex: "status",
      key: "status",
    },
    {
           title: "Operation",
           key: "Operation",
           fixed: "right",
           render: (text, record) => (
             <span>
               <button
                 onClick={() => handleEdit(record)}
                 className="button muted-button"
               >
                 Edit
               </button>
               <Divider type="vertical" />
               <button
                 onClick={() => handleDelete(record.id)}
                 className="button muted-button"
               >
                 Delete
               </button>
             </span>
           )
    }
  ]

  const [pagination, setPagination] = useState({
    current: 1,
    pageSize: 5,
    total: 0,
  });

  useEffect(() => {
    getList();
  }, [pagination.current, pagination.pageSize]);

  const getList = async () => {
    let params = {
      page: pagination.current-1,
      size: pagination.pageSize,
    };

    let res = await queryIncidents(params);
    setDataSource(res.data.content);
    setPagination({
      ...pagination,
      total: res.data.totalElements,
    });
  };

  const handleEdit = (row) => {
    const [event] = dataSource.filter((item) => item.id === row.id);
    console.log(event);
    setMkItem(event);
    setShowEdit(true);
  };

  const handleTableChange = async(reqParam) =>{
    console.log(reqParam)
    setPagination({
      ...pagination,
      current: reqParam.current,
      pageSize: reqParam.pageSize
    });
    getList();
  }

  const handleDelete = async (id) =>{
    let res = await deleteIncident(id);
    if(res.data.code == '200'){
        message.success("success",3,()=>{})
        getList();
    }else{
        message.error(res.data.message,5,()=>{})
    }
  }

  return (
    <>
      <div>
      <AddIncident getList={getList}/>
        <Table columns={columns}
        dataSource={dataSource}
        rowKey={(record) => record.id}
        pagination={pagination}
        onChange={handleTableChange}
        >
        </Table>
      </div>

    {showEdit && (
      <UpdIncident
        record={mkItem}
        setShowEdit={setShowEdit}
        getList={getList}
      />
    )}

    </>
  )
}

export default App
