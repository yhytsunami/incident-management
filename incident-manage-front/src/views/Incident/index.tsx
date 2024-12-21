import React, { useState, useEffect } from "react";

import AddIncident from "../../components/AddIncident";
import UpdIncident from "../../components/UpdIncident";

import { queryIncidents, deleteIncident } from "../../api/Incident";

const Incident = () => {
  const [dataSource, setDataSource] = useState();
  const [pagination, setPagination] = useState({
    current: 1,
    pageSize: 10,
    total: 0,
  });
  const columns = [
    {
      title: "Incident Name",
      dataIndex: "title",
      key: "title",
    },
    {
      title: "Incident Description",
      dataIndex: "description",
      key: "description",
      style: { minWidth: "200px" },
    },
    {
      title: "Create Date",
      dataIndex: "createdAt",
      key: "createdAt",
      render: (text, record) => (
        <span>{dayjs(record.createdAt).format("YYYY-MM-DD HH:mm:ss")}</span>
      ),
      style: { minWidth: "200px" },
    },
    {
      title: "Update Date",
      dataIndex: "updatedAt",
      key: "updatedAt",
      render: (text, record) => (
        <span>{dayjs(record.updatedAt).format("YYYY-MM-DD HH:mm:ss")}</span>
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
                 onClick={() => handleEdit(record.id)}
                 className="button muted-button"
               >
                 Edit
               </button>
               <Divider type="vertical" />
               <button
                 onClick={() => handleDelete(record)}
                 className="button muted-button"
               >
                 Delete
               </button>
             </span>
           )
    }
  ]


  useEffect(() => {
    getList();
  }, [pagination.current, pagination.pageSize]);

  const getList = async () => {
    let params = {
      page: pagination.current-1,
      size: pagination.pageSize,
      title: searchText,
    };

    let res = await fetchList(params);
    console.log(res, "res===");
    setDataSource(res.data.content);
    setPagination({
      ...pagination,
      total: res.data.totalElements,
    });
  };

  const handleSearch = () => {
    getList();
  };

  const handleEdit = (id) => {
    const [data] = dataSource.filter((item) => item.id === id);
    setselectedEvent(data);
  };

  const handleDelete = (data) => {
        try {
          let res = deleteItem(data.id);
        } catch (error) {
        }
      };

  return (
    <div>
      <Table
        columns={columns}
        dataSource={dataSource}
        pagination={true}
      ></Table>
    </div>
  );
};

export default Incident;
