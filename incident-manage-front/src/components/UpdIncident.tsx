import React, { useState } from "react";
import { updateIncident } from "../api/Incidents";
import { Button,message, Modal } from "antd";

const UpdIncident = ({ record, setShowEdit, getList }) => {
  const [name, setName] = useState(record.name);
  const [description, setDescription] = useState(record.description);
  const [status, setStatus] = useState(record.status);
  const [visiable, setVisiable] = useState(true);

  const handleStatusChange = (event) => {
    setStatus(event.target.value);
  };

  const options = [
    { value: "START", label: "START" },
    { value: "DEALING", label: "DEALING" },
    { value: "END", label: "END" },
  ];

  const onOk = async () => {

    const data = {
      name,
      description,
      status,
    };

    let res = await updateIncident(data);

    if(res.data.code == '200'){
        message.success("success",3,()=>{})
        getList();
        closeModal();
    }else{
        message.error(res.data.message,5,()=>{})
    }
  };

  const closeModal = () => {
    setName('');
    setStatus('');
    setDescription('');
    setVisiable(false);
    setShowEdit(false);
  };

  return (
    <div >
            <Modal
              title="AddIncident"
              open={visiable}
              onOk={onOk}
              onCancel={closeModal}
              afterClose={closeModal}
            >
          <form>
            <div>
            <label htmlFor="name">Incident Name</label>
            <input
              id="name"
              type="text"
              name="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
            </div>
            <div>
            <label htmlFor="description">Incident Description</label>
            <input
              id="description"
              type="text"
              name="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
            </div>
            <div>
            <label htmlFor="status">Incident Status</label>
            <select id="status" value={status} onChange={handleStatusChange}>
              <option value="">--Please choose an option--</option>
              {options.map((option) => (
                <option key={option.value} value={option.value}>
                  {option.label}
                </option>
              ))}
            </select>
            </div>
          </form>
        </Modal>
    </div>
  );
};

export default UpdIncident;
