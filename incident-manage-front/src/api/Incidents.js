import axios from "axios";

const reqInstance = axios.create({
  baseURL: "/api/incidents",
  timeout: 10000,
});

export function queryIncidents(params) {
  return reqInstance({
    method: "GET",
    params,
  });
}

export function addIncident(data) {
  return reqInstance({
    url: "/incident",
    method: "POST",
    data,
  });
}

export function updateIncident(data) {
  return reqInstance({
    url: `/${data.id}`,
    method: "PUT",
    data,
  });
}

export function deleteIncident(id) {
  return reqInstance({
    url: `/${id}`,
    method: "DELETE",
  });
}
