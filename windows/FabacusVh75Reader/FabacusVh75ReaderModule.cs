using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Com.Reactlibrary.FabacusVh75Reader
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class FabacusVh75ReaderModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="FabacusVh75ReaderModule"/>.
        /// </summary>
        internal FabacusVh75ReaderModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "FabacusVh75Reader";
            }
        }
    }
}
