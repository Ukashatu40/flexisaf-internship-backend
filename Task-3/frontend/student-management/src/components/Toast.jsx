import { useEffect } from 'react';

function Toast({ message, type, onClose }) {
  useEffect(() => {
    const timer = setTimeout(onClose, 3000);
    return () => clearTimeout(timer);
  }, [onClose]);

  return (
    <div className={`fixed top-4 right-4 p-4 rounded-lg shadow-lg text-white ${type === 'error' ? 'bg-red-600' : 'bg-green-600'} animate-slide-in`}>
      <div className="flex items-center">
        <i className={`fas fa-${type === 'error' ? 'exclamation-circle' : 'check-circle'} mr-2`}></i>
        <span>{message}</span>
        <button onClick={onClose} className="ml-4 text-white hover:text-gray-200">
          <i className="fas fa-times"></i>
        </button>
      </div>
    </div>
  );
}

export default Toast;
