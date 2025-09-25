function StudentTable({ students, loading, onView, onEdit, onDelete }) {
  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden">
      {loading ? (
        <div className="p-4">
          <div className="animate-pulse">
            <div className="h-8 bg-gray-200 rounded mb-2"></div>
            {[...Array(3)].map((_, i) => (
              <div key={i} className="h-12 bg-gray-200 rounded mb-2"></div>
            ))}
          </div>
        </div>
      ) : students.length === 0 ? (
        <div className="p-4 text-center text-gray-600">
          No students found. Add a new student to get started!
        </div>
      ) : (
        <table className="w-full border-collapse">
          <thead>
            <tr className="bg-gray-100">
              <th className="p-3 text-left text-gray-700 font-semibold">ID</th>
              <th className="p-3 text-left text-gray-700 font-semibold">Name</th>
              <th className="p-3 text-left text-gray-700 font-semibold">Department</th>
              <th className="p-3 text-left text-gray-700 font-semibold">Actions</th>
            </tr>
          </thead>
          <tbody>
            {students.map(student => (
              <tr key={student.id} className="border-b hover:bg-gray-50 transition duration-200">
                <td className="p-3">{student.id}</td>
                <td className="p-3">{student.name}</td>
                <td className="p-3">{student.department}</td>
                <td className="p-3 flex space-x-2">
                  <button
                    onClick={() => onView(student.id)}
                    className="flex items-center bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700 transition duration-300"
                  >
                    <i className="fas fa-eye mr-1"></i> View
                  </button>
                  <button
                    onClick={() => onEdit(student)}
                    className="flex items-center bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600 transition duration-300"
                  >
                    <i className="fas fa-edit mr-1"></i> Edit
                  </button>
                  <button
                    onClick={() => onDelete(student.id)}
                    className="flex items-center bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700 transition duration-300"
                  >
                    <i className="fas fa-trash mr-1"></i> Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default StudentTable;
