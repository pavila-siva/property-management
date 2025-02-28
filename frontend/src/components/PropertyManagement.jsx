import React, { useEffect, useState } from "react";
import { Table, Button, Modal, Input, Space } from "antd";

const API_URL = "http://localhost:8081/houses";

const PropertyManagement = () => {
  const [properties, setProperties] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isDetailsModalOpen, setIsDetailsModalOpen] = useState(false);
  const [selectedProperty, setSelectedProperty] = useState(null);
  const [formData, setFormData] = useState({
    name: "",
    address: "",
    rent: "",
    noOfRoom: "",
  });
  const [editingId, setEditingId] = useState(null);

  useEffect(() => {
    fetchProperties();
  }, []);

  const fetchProperties = async () => {
    try {
      const res = await fetch(`${API_URL}/all`);
      const data = await res.json();
      //console.log("Fetched properties:", data);
      setProperties(data);
    } catch (error) {
      console.error("Error fetching properties:", error);
    }
  };

  const handleAdd = () => {
    setEditingId(null);
    setFormData({ name: "", address: "", rent: "", noOfRoom: "" });
    setIsModalOpen(true);
  };

  const handleEdit = (property) => {
    setEditingId(property.id);
    setFormData(property);
    setIsModalOpen(true);
  };

  const handleDelete = async (id) => {
    if (!id) {
      console.error("Error: Property ID is undefined.");
      return;
    }

    try {
      const response = await fetch(`${API_URL}/delete/${id}`, {
        method: "DELETE",
      });

      if (!response.ok) {
        throw new Error("Failed to delete property");
      }

      //console.log(`Property with ID ${id} deleted successfully`);
      fetchProperties();
    } catch (error) {
      console.error("Error deleting property:", error);
    }
  };

  const handleSubmit = async () => {
    try {
      const method = editingId ? "PUT" : "POST";
      const url = editingId
        ? `${API_URL}/edit/${editingId}`
        : `${API_URL}/create`;

      await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      setIsModalOpen(false);
      fetchProperties();
    } catch (error) {
      console.error("Error saving property:", error);
    }
  };

  const handleShowDetails = (property) => {
    setSelectedProperty(property);
    setIsDetailsModalOpen(true);
  };

  const columns = [
    { title: "Name", dataIndex: "name", key: "name" },
    { title: "Address", dataIndex: "address", key: "address" },
    { title: "Rent", dataIndex: "rent", key: "rent" },
    { title: "Rooms", dataIndex: "noOfRoom", key: "noOfRoom" },
    {
      title: "Actions",
      render: (property) => (
        <Space>
          <Button onClick={() => handleShowDetails(property)}>Details</Button>
          <Button type="primary" onClick={() => handleEdit(property)}>
            Edit
          </Button>
          <Button
            type="primary"
            danger
            onClick={() => handleDelete(property?.id)}
          >
            Delete
          </Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <h2>Property Management</h2>
      <Button type="primary" onClick={handleAdd}>
        Add Property
      </Button>
      <Table
        dataSource={properties}
        columns={columns}
        rowKey={(record) => record.id || record.name}
      />

      {/* Add / Edit Modal */}
      <Modal
        title={editingId ? "Edit Property" : "Add Property"}
        open={isModalOpen}
        onCancel={() => setIsModalOpen(false)}
        onOk={handleSubmit}
      >
        <Input
          placeholder="Name"
          value={formData.name}
          onChange={(e) => setFormData({ ...formData, name: e.target.value })}
        />
        <Input
          placeholder="Address"
          value={formData.address}
          onChange={(e) =>
            setFormData({ ...formData, address: e.target.value })
          }
        />
        <Input
          placeholder="Rent"
          type="number"
          value={formData.rent}
          onChange={(e) => setFormData({ ...formData, rent: e.target.value })}
        />
        <Input
          placeholder="Rooms"
          type="number"
          value={formData.noOfRoom}
          onChange={(e) =>
            setFormData({ ...formData, noOfRoom: e.target.value })
          }
        />
      </Modal>

      {/* Details Modal */}
      <Modal
        title="Property Details"
        open={isDetailsModalOpen}
        onCancel={() => setIsDetailsModalOpen(false)}
        footer={null}
      >
        {selectedProperty && (
          <div>
            <p>
              <strong>Name:</strong> {selectedProperty.name}
            </p>
            <p>
              <strong>Address:</strong> {selectedProperty.address}
            </p>
            <p>
              <strong>Rent:</strong> {selectedProperty.rent}
            </p>
            <p>
              <strong>Rooms:</strong> {selectedProperty.noOfRoom}
            </p>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default PropertyManagement;
