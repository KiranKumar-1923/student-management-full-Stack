import React, { useEffect, useState } from "react";
import axios from "axios";

function StudentComponent() {





  const [students, setStudents] = useState([]);
  const [name, setName] = useState("");
  const [course, setCourse] = useState("");
   const [id, setId] = useState(null);
   const [search, setSearch] = useState("");
   const [page, setPage] = useState(0);
const [size] = useState(5);

const paginatedStudents =
  (students || []).slice(page * size, page * size + size);

const totalPages = Math.ceil((students || []).length / size);


const editStudent = (student) => {
  setName(student.name);
  setCourse(student.course);
  setId(student.id);   // ⭐ very important for update
};
  useEffect(() => {
    loadStudents();
  }, []);

 const loadStudents = () => {
  axios.get("http://localhost:8080/student")
    .then(res => {
      console.log("FULL RESPONSE", res.data);
      setStudents(res.data.data || []);
    })
    .catch(err => {
      console.log(err);
      setStudents([]);
    });
};
const saveStudent = () => {

  if(name.trim() === "" || course.trim() === ""){
    alert("Please enter name and course");
    return;
  }

  const student = {
    id: id,
    name: name,
    course: course
  };

  if(id === null){
    // ⭐ CREATE
    axios.post("http://localhost:8080/student", student)
      .then(() => loadStudents());
  } else {
    // ⭐ UPDATE
    axios.put("http://localhost:8080/student", student)
      .then(() => loadStudents());
  }

  setName("");
  setCourse("");
  setId(null);
};
  const deleteStudent = (id) => {
    axios.delete("http://localhost:8080/student/" + id)
      .then(() => loadStudents())
      .catch(err => console.log(err));
  };

  // ⭐⭐⭐ RETURN MUST BE INSIDE FUNCTION
  return (
    <div className="container mt-5">

      <h2 className="text-center">Student Management System</h2>

      <div className="row mt-4 mb-3">
        <div className="col">
          <input
            className="form-control"
            placeholder="Enter Name"
            value={name}
            onChange={(e)=>setName(e.target.value)}
          />
        </div>

        <div className="col">
          <input
            className="form-control"
            placeholder="Enter Course"
            value={course}
            onChange={(e)=>setCourse(e.target.value)}
          />
        </div>

        <div className="col">
          <button className="btn btn-primary" onClick={saveStudent}>
            Add Student
          </button>
        </div>
      </div>


      <div className="row mb-3">
  <div className="col-4">
    <input
      className="form-control"
      placeholder="Search by Name"
      value={search}
      onChange={(e)=>setSearch(e.target.value)}
    />
  </div>
</div>

      <table className="table table-bordered">
        <thead className="table-dark">
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Course</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
       {paginatedStudents
  .filter(s =>
    s.name.toLowerCase().includes(search.toLowerCase())
  )
  .map(s => (
            <tr key={s.id}>
              <td>{s.id}</td>
              <td>{s.name}</td>
              <td>{s.course}</td>
              <td>

  <button
    className="btn btn-warning btn-sm me-2"
    onClick={()=>editStudent(s)}
  >
    Edit
  </button>

  <button
    className="btn btn-danger btn-sm"
    onClick={()=>deleteStudent(s.id)}
  >
    Delete
  </button>

</td>
            </tr>
          ))}
        </tbody>

      </table>

      <div className="text-center mt-3">

  <button
    className="btn btn-secondary me-2"
    disabled={page === 0}
    onClick={() => setPage(page - 1)}
  >
    Previous
  </button>

  <span className="me-2">
    Page {page + 1} of {totalPages}
  </span>

  <button
    className="btn btn-secondary"
    disabled={page + 1 === totalPages}
    onClick={() => setPage(page + 1)}
  >
    Next
  </button>

</div>

    </div>
  );
}

export default StudentComponent;