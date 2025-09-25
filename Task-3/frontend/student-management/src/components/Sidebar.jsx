function Sidebar({ fetchStudents, setSelectedStudent }) {
  return (
    <div className="w-64 bg-gradient-to-b from-blue-800 to-blue-600 text-white p-6 shadow-lg">
      <h1 className="text-2xl font-bold mb-8">Student Dashboard</h1>
      <nav>
        <button
          onClick={() => {
            fetchStudents();
            setSelectedStudent(null);
          }}
          className="w-full text-left py-2 px-4 rounded hover:bg-blue-700 transition duration-300"
        >
          <i className="fas fa-users mr-2"></i> All Students
        </button>
      </nav>
    </div>
  );
}

export default Sidebar;