import React, { useState } from 'react';
import { Rate } from 'antd';
const desc = ['terrible', 'bad', 'normal', 'good', 'wonderful'];
function Rating({onUpdate}) {
  const [value, setValue] = useState(3);
  // console.log('newValue:',value)
  onUpdate(value)
  return (
    <span>
      <Rate tooltips={desc} onChange={setValue} value={value} />
      {value ? <span className="ant-rate-text">{desc[value - 1]}</span> : ''} 
    </span>
  );
};
export default Rating;