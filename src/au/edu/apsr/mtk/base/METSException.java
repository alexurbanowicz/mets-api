/**
 * 
 * Copyright 2008 The Australian National University (ANU)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package au.edu.apsr.mtk.base;

/**
 * An exception class for exceptions in METS Toolkit code
 * 
 * @author Scott Yeadon, ANU 
 */
public class METSException extends Exception
{
    /**
     * create a METSException
     * 
     * @param reason
     *            string describing reason for the exception
     */
    public METSException(String reason)
    {
        super(reason);
    }
    
    
    /**
     * create a METSException
     * 
     * @param reason
     *            string describing reason for the exception
     * @param cause
     *            A Throwable describing the cause of the exception
     */
    public METSException(String reason,
                        Throwable cause)
    {
        super(reason, cause);
    }
    
    
    /**
     * create a METSException
     * 
     * @param cause
     *            A Throwable describing the cause of the exception
     */
    public METSException(Throwable cause)
    {
        super(cause);
    }
    
    
    /**
     * Obtain the reason for the exception
     *
     * @return String
     *          The message text of the exception
     */
    public String getMessage()
    {
        return super.getMessage();
    }
}