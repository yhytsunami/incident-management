import React, { useState } from "react";
import { addIncident } from "../api/incidents";
import { Button,message, Modal } from "antd";

const AddIncident = ({getList}) => {
  const [visiable, setVisiable] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [status, setStatus] = useState("");
  const [messageApi, contextHolder] = message.useMessage();

  const handleStatusChange = (event) => {
    setStatus(event.target.value);
  };

  const onOk = async () => {

    const data = {
      name,
      description,
      status,
    };

    let res = await addIncident(data);
    console.log('addIncident',res)

    if(res.data.code == '200'){
        message.success("success",3,()=>{})
        getList();
        closeModal();
    }else{
        message.error(res.data.message,5,()=>{})
    }
  };

  const options = [
    { value: "START", label: "START" },
    { value: "DEALING", label: "DEALING" },
    { value: "END", label: "END" },
  ];

  const closeModal = () => {
    setName('');
    setStatus('');
    setDescription('');
    setVisiable(false);
  };

  return (
    <>
      <div>
        <Button onClick={() => setVisiable(true)}>Add Incident</Button>
        <Modal
          title="AddIncident"
          open={visiable}
          onOk={onOk}
          onCancel={closeModal}
          afterClose={closeModal}
        >
          <form>
          <div>
                            <label htmlFor="name">Incident name</label>
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

    </>
  );
};

export default AddIncident;
